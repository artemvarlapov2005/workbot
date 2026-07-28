package ru.workbot.application.usecase

import org.springframework.stereotype.Service
import ru.workbot.application.exception.TokenIssueForbiddenException
import ru.workbot.application.port.input.command.IssueTokenCommand
import ru.workbot.application.port.input.usecase.IssueTokenUseCase
import ru.workbot.application.port.output.client.ClientsStore
import ru.workbot.application.port.output.client.HashedPassword
import ru.workbot.application.port.output.client.JwtAdapter
import ru.workbot.application.port.output.client.PasswordHasher
import java.time.Instant

class IssueTokenUseCase(
    private val clientsStore: ClientsStore,
    private val passwordHasher: PasswordHasher,
    private val jwtAdapter: JwtAdapter
) : IssueTokenUseCase {
    override fun execute(command: IssueTokenCommand): String {
        val client = clientsStore.getById(command.clientId) ?: throw TokenIssueForbiddenException(
            command.clientId,
            command.scopes
        )

        if (!passwordHasher.matches(command.clientSecret, client.clientHash)) {
            throw TokenIssueForbiddenException(command.clientId, command.scopes)
        }

        val allowedScopes = client.scopes.map { it.toScopeString() }

        if (command.scopes.any { it !in allowedScopes }) {
            throw TokenIssueForbiddenException(command.clientId, command.scopes)
        }

        return jwtAdapter.create(
            subject = client.clientId,
            scopes = command.scopes,
            expiresAt = Instant.now().plusSeconds(10 * 60),
        )
    }
}