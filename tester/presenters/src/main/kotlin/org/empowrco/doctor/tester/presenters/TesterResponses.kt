package org.empowrco.doctor.tester.presenters

import kotlinx.serialization.Serializable

@Serializable
data class ExecuteTestsResponse(
    val output: String,
)
