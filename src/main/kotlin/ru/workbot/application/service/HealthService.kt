package ru.workbot.application.service

import ru.workbot.application.port.input.CheckHealthUseCase

class HealthService : CheckHealthUseCase {
    override fun check() = Unit
}
