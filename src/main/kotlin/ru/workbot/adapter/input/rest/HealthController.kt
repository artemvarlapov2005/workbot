package ru.workbot.adapter.input.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.workbot.application.port.input.usecase.CheckHealthUseCase

@RestController
@RequestMapping("/api/v1")
class HealthController(
    private val checkHealth: CheckHealthUseCase,
) {
    @GetMapping("/health")
    fun health(): ResponseEntity<Void> {
        checkHealth.check()
        return ResponseEntity.ok().build()
    }
}
