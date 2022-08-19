package org.empowrco.doctor.models

import kotlinx.datetime.LocalDateTime
import java.util.UUID

data class Feedback(
    val id: UUID,
    val attempt: Int,
    val feedback: String,
    val regexMatcher: String,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
) {
    val regex: Regex = regexMatcher.toRegex()
}