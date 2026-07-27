package ru.workbot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.workbot.application.port.input.CheckHealthUseCase
import ru.workbot.application.service.HealthService

@Configuration
class ApplicationConfiguration {
    @Bean
    fun checkHealthUseCase(): CheckHealthUseCase = HealthService()
}
