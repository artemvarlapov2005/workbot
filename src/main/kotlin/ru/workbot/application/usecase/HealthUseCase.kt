package ru.workbot.application.usecase

import org.springframework.stereotype.Component
import ru.workbot.application.port.input.usecase.CheckHealthUseCase

class HealthUseCase : CheckHealthUseCase {
    override fun check() = Unit
}
