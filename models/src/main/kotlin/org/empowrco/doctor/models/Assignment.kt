package org.empowrco.doctor.models

import kotlinx.datetime.LocalDateTime
import java.util.UUID

data class Assignment(
    val id: UUID,
    val referenceId: String,
    val expectedOutput: String,
    val feedback: List<Feedback>,
    val instructions: String,
    val solution: String,
    val successMessage: String,
    val failureMessage: String,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val totalAttempts: Int,
)
