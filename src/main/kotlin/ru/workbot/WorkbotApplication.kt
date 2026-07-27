package ru.workbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkbotApplication

fun main(args: Array<String>) {
    runApplication<WorkbotApplication>(*args)
}
