package ru.workbot.adapter.output.security

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.workbot.application.port.output.client.HashedPassword
import ru.workbot.application.port.output.client.PasswordHasher

@Component
class PasswordHasher(
    private val passwordEncoder: PasswordEncoder,
) : PasswordHasher {
    override fun hashPassword(password: String): HashedPassword =
        HashedPassword(passwordEncoder.encode(password))

    override fun matches(
        password: String,
        hashedPassword: HashedPassword
    ): Boolean = passwordEncoder.matches(
        password,
        hashedPassword.hashedPassword)
}