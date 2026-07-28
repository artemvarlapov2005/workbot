package ru.workbot.domain

data class StakeHolder(
    val id: String,
    val displayName: String,
    val availableIndustries: Set<IndustryId>
) {
    init {
        require(displayName.isNotBlank()) { "displayName cannot be blank" }
        require(id.isNotBlank()) { "id cannot be blank" }
    }
}
