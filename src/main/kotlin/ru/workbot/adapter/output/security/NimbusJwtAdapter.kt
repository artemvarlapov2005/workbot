package ru.workbot.adapter.output.security

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jose.proc.SingleKeyJWSKeySelector
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import ru.workbot.application.port.output.client.JwtAdapter
import ru.workbot.application.port.output.client.ValidatedJwt
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Clock
import java.time.Instant
import java.util.Date

/**
 * Технический JWT-адаптер. Приватный ключ используется только для выпуска
 * токенов, публичный — только для проверки их подписи.
 */
class NimbusJwtAdapter(
    private val privateKey: RSAPrivateKey,
    private val publicKey: RSAPublicKey,
    private val issuer: String,
    private val audience: String,
    private val clock: Clock = Clock.systemUTC(),
) : JwtAdapter {
    private val jwtProcessor = DefaultJWTProcessor<SecurityContext>().apply {
        jwsKeySelector = SingleKeyJWSKeySelector(JWSAlgorithm.RS256, publicKey)
        jwtClaimsSetVerifier = object : DefaultJWTClaimsVerifier<SecurityContext>(
            setOf(audience),
            JWTClaimsSet.Builder().issuer(issuer).build(),
            setOf("sub", "scope", "exp"),
            emptySet(),
        ) {
            override fun currentTime(): Date = Date.from(clock.instant())
        }
    }

    override fun create(
        subject: String,
        scopes: Set<String>,
        expiresAt: Instant,
    ): String {
        require(subject.isNotBlank()) { "JWT subject must not be blank" }
        require(scopes.isNotEmpty()) { "JWT must contain at least one scope" }
        require(expiresAt.isAfter(clock.instant())) { "JWT expiration must be in the future" }

        val now = clock.instant()
        val claims = JWTClaimsSet.Builder()
            .subject(subject)
            .issuer(issuer)
            .audience(audience)
            .issueTime(Date.from(now))
            .notBeforeTime(Date.from(now))
            .expirationTime(Date.from(expiresAt))
            .claim("scope", scopes.sorted())
            .build()

        return SignedJWT(
            JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build(),
            claims,
        ).also { it.sign(RSASSASigner(privateKey)) }
            .serialize()
    }

    override fun validate(token: String): ValidatedJwt {
        try {
            val claims = jwtProcessor.process(token, null)

            return ValidatedJwt(
                subject = claims.subject ?: throw JwtValidationException("JWT subject is missing"),
                scopes = claims.getStringListClaim("scope").toSet(),
                expiresAt = requireNotNull(claims.expirationTime).toInstant(),
            )
        } catch (exception: JwtValidationException) {
            throw exception
        } catch (exception: Exception) {
            throw JwtValidationException("JWT is malformed", exception)
        }
    }
}

class JwtValidationException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
