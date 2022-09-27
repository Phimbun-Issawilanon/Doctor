package org.empowrco.doctor.assignment.presenters

import kotlinx.serialization.Serializable

@Serializable
data class RunResponse(
    val output: String,
    val expectedOutput: String,
    val diff: String?,
    val success: Boolean,
)

@Serializable
data class SubmitResponse(
    val output: String,
    val expectedOutput: String,
    val feedback: String,
    val success: Boolean,
    val finalAttempt: Boolean,
    val diff: String?,
)

@Serializable
object CreateAssignmentResponse
