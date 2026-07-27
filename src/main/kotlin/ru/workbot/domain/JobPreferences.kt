package ru.workbot.domain

data class JobPreferences(
    val title: String,
    val description: String,
    val address: String?,
) {
    init {
        require(title.isNotEmpty()) { "title cannot be empty" }
        require(description.isNotEmpty()) { "description cannot be empty" }
    }
}