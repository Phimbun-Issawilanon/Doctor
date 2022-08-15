package org.empowrco.doctor.api.assignment

import kotlinx.serialization.Serializable

@Serializable
data class RunResponse(val output: String)

@Serializable
data class SubmitResponse(val output: String, val feedback: String)