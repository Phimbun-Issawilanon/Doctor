package org.empowrco.doctor.run.presenters

import kotlinx.serialization.Serializable

@Serializable
data class RunRequest(
    val language: String,
    val code: String,
)
