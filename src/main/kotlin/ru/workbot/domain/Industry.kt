package ru.workbot.domain

import java.util.UUID

data class Industry(
    val id: IndustryId,
    val title: String
)

@JvmInline
value class IndustryId(val value: UUID)