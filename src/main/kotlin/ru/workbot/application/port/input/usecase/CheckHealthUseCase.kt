package ru.workbot.application.port.input.usecase

/**
 * Входной порт: действие, которое могут вызывать любые входные адаптеры.
 */
fun interface CheckHealthUseCase {
    fun check()
}