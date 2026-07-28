package ru.workbot.application.port.output.client

import java.time.Instant

interface JwtAdapter {
    fun create(
        subject: String,
        scopes: Set<String>,
        expiresAt: Instant,
    ): String

    fun validate(token: String): ValidatedJwt
}

data class ValidatedJwt(
    val subject: String,
    val scopes: Set<String>,
    val expiresAt: Instant,
)