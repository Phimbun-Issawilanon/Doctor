package org.empowrco.doctor.api.assignment

import kotlinx.serialization.Serializable

@Serializable
data class RunRequest(val language: String, val code: String)

@Serializable
data class SubmitRequest(
    val code: String,
    val assignmentId: String,
    val language: String,
    val email: String,
    val attempt: Int,
)

@Serializable
data class CreateAssignmentRequest(

)