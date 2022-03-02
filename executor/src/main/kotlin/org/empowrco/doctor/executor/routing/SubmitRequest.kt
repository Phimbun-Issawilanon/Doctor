package org.empowrco.doctor.executor.routing

@kotlinx.serialization.Serializable
class SubmitRequest(val code: String, val assignmentId: String)