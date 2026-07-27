package ru.workbot.domain

import java.util.UUID

data class Job(
    val id: JobId,
    val preferences: JobPreferences,
    val experience: Experience?,
    val gradeLevel: GradeLevel,
    val industryId: IndustryId,
    val companyId: CompanyId,
)

@JvmInline
value class JobId(val value: UUID)

@JvmInline
value class GradeLevel(val value: Int) {
    init {
        require(value >= 0) { "Grade level cannot be negative" }
    }
}

data class Experience(
    val min: Int,
    val max: Int?,
) {
    init {
        require(min <= (max ?: Int.MAX_VALUE)) { "Experience must be in range [min, max]" }
        require(min >= 0) { "Experience must be at least 0" }
    }
}