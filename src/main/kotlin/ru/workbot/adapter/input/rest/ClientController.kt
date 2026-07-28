package ru.workbot.adapter.input.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.workbot.application.port.input.command.IssueTokenCommand
import ru.workbot.application.port.input.usecase.IssueTokenUseCase

@RestController
@RequestMapping("/api/client")
class ClientController(
    private val issueTokenUseCase: IssueTokenUseCase
) {

    @PostMapping("/issue")
    fun issue(@RequestBody request: IssueRequest): ResponseEntity<IssueResponse> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(IssueResponse(issueTokenUseCase.execute(request.toCommand())))

}

data class IssueRequest(val clientId: String,
                        val clientSecret: String,
                        val scopes: Set<String>) {
    fun toCommand() = IssueTokenCommand(clientId, clientSecret, scopes)
}

data class IssueResponse(val token: String)