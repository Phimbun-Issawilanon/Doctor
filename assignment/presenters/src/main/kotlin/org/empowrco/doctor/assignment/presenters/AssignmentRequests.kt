package org.empowrco.doctor.assignment.presenters

import kotlinx.serialization.Serializable

@Serializable
data class RunRequest(val language: String, val code: String)

@Serializable
data class SubmitRequest(
    val code: String,
    val referenceId: String,
    val language: String,
    val email: String,
    val attempt: Int,
)

@Serializable
data class CreateAssignmentRequest(
    val referenceId: String,
    val expectedOutput: String,
    val feedback: List<Feedback>,
    val instructions: String,
    val solution: String,
    val successMessage: String,
    val failureMessage: String,
    val totalAttempts: Int,
) {
    @Serializable
    data class Feedback(
        val attempt: Int,
        val feedback: String,
        val regexMatcher: String,
    )
}