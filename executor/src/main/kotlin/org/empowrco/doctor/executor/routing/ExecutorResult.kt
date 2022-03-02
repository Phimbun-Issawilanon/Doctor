package org.empowrco.doctor.executor.routing

import kotlinx.serialization.Serializable

@Serializable
data class ExecutorResult(val output: String, val code: String)