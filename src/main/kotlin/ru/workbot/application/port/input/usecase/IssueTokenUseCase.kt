package ru.workbot.application.port.input.usecase

import ru.workbot.application.port.input.command.IssueTokenCommand

interface IssueTokenUseCase {
    fun execute(command: IssueTokenCommand) : String
}