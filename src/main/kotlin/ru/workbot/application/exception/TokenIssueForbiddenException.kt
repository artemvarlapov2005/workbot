package ru.workbot.application.exception

class TokenIssueForbiddenException(val clientId: String, val scopes: Set<String>) : RuntimeException()