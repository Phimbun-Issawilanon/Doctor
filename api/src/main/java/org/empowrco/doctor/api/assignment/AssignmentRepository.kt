package org.empowrco.doctor.api.assignment

import org.empowrco.doctor.handler.CodeHandler
import org.empowrco.doctor.models.ExecutorResponse

interface AssignmentRepository {
    suspend fun executeCode(language: String, code: String): ExecutorResponse
}

internal class RealAssignmentRepository(private val handler: CodeHandler) : AssignmentRepository {
    override suspend fun executeCode(language: String, code: String): ExecutorResponse {
        return handler.execute(language, code)
    }
}