package ru.workbot.application.port.output.client

import ru.workbot.domain.IndustryId

data class Client(
    val clientId: String,
    val scopes: Set<ClientScope>,
    val clientHash: HashedPassword,
)

@JvmInline
value class HashedPassword(val hashedPassword: String)

sealed interface ClientScope {
    val type: ScopeType
    val industryId: IndustryId

    fun toScopeString(): String = "${type.id}:${industryId.value}"

    data class ManageCompaniesInIndustryScope(
        override val industryId: IndustryId
    ): ClientScope {
        override val type: ScopeType = ScopeType.MANAGE_COMPANIES
    }

    data class ManageJobsInIndustryScope(
        override val industryId: IndustryId
    ): ClientScope {
        override val type: ScopeType = ScopeType.MANAGE_JOBS
    }
}

enum class ScopeType(val id: String) {
    MANAGE_COMPANIES("COMPANIES"),
    MANAGE_JOBS("JOBS"),
}