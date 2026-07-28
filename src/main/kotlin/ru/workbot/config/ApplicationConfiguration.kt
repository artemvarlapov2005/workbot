package ru.workbot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import ru.workbot.adapter.output.security.NimbusJwtAdapter
import ru.workbot.application.port.input.usecase.CheckHealthUseCase
import ru.workbot.application.port.input.usecase.IssueTokenUseCase
import ru.workbot.application.port.output.client.ClientsStore
import ru.workbot.application.port.output.client.JwtAdapter
import ru.workbot.application.port.output.client.PasswordHasher
import ru.workbot.application.usecase.HealthUseCase
import ru.workbot.application.usecase.IssueTokenUseCase as IssueTokenUseCaseHandler
import java.security.KeyPair
import java.security.KeyPairGenerator

@Configuration
class ApplicationConfiguration {
    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun checkHealthUseCase(): CheckHealthUseCase = HealthUseCase()

    @Bean
    fun issueTokenUseCase(
        clientsStore: ClientsStore,
        passwordHasher: PasswordHasher,
        jwtAdapter: JwtAdapter,
    ): IssueTokenUseCase = IssueTokenUseCaseHandler(
        clientsStore = clientsStore,
        passwordHasher = passwordHasher,
        jwtAdapter = jwtAdapter,
    )

    @Bean
    fun jwtKeyPair(): KeyPair = KeyPairGenerator.getInstance("RSA")
        .apply { initialize(2048) }
        .generateKeyPair()

    @Bean
    fun jwtAdapter(
        jwtKeyPair: KeyPair,
        @Value("\${workbot.jwt.issuer}") issuer: String,
        @Value("\${workbot.jwt.audience}") audience: String,
    ): JwtAdapter = NimbusJwtAdapter(
        privateKey = jwtKeyPair.private as java.security.interfaces.RSAPrivateKey,
        publicKey = jwtKeyPair.public as java.security.interfaces.RSAPublicKey,
        issuer = issuer,
        audience = audience,
    )
}
