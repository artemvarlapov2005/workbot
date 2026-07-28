package ru.workbot.application.port.output.client

interface PasswordHasher {
    fun hashPassword(password: String): HashedPassword

    fun matches(password: String, hashedPassword: HashedPassword): Boolean
}