package ru.workbot.application.port.output.client

interface ClientsStore {
    fun getById(clientId: String): Client?
}