package org.empowrco.doctor.run.presenters

import kotlinx.serialization.Serializable

@Serializable
data class RunResponse(
    val output: String,
    val success: Boolean,
)
