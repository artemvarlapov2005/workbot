package ru.workbot.application.port.input.command

data class IssueTokenCommand(
    val clientId: String,
    val clientSecret: String,
    val scopes: Set<String>
)