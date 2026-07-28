package ru.workbot.domain

import java.util.UUID

class Company(
    val id: CompanyId,
    val name: String,
    val url: String?,
) {
    init {
        require(name.isNotBlank()) { "name cannot be blank" }
    }
}

@JvmInline
value class CompanyId(val value: UUID)