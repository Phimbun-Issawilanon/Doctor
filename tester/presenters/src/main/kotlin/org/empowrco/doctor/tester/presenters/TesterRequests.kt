package org.empowrco.doctor.tester.presenters

import kotlinx.serialization.Serializable

@Serializable
data class ExecuteTestRequest(
    val language: String,
    val unitTests: String,
    val code: String,
)
