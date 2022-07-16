package org.empowrco.doctor.api.assignment

@kotlinx.serialization.Serializable
data class RunRequest(val language: String, val code: String)
