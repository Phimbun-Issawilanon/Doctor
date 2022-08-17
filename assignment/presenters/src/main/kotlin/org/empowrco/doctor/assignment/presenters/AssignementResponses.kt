package org.empowrco.doctor.assignment.presenters

import kotlinx.serialization.Serializable

@Serializable
data class RunResponse(val output: String)

@Serializable
data class SubmitResponse(
    val output: String,
    val feedback: String,
    val success: Boolean,
)

@Serializable
data class CreateAssignmentResponse(val uuid: String)