package org.empowrco.doctor.api.assignment

import org.empowrco.doctor.handler.CodeHandler
import org.empowrco.doctor.models.Assignment
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.sources.AssignmentSource
import java.util.UUID

interface AssignmentRepository {
    suspend fun executeCode(language: String, code: String): ExecutorResponse
    suspend fun getAssignment(uuid: UUID): Assignment?
}

internal class RealAssignmentRepository(
    private val handler: CodeHandler,
    private val assignmentSource: AssignmentSource,
) : AssignmentRepository {
    override suspend fun executeCode(language: String, code: String): ExecutorResponse {
        return handler.execute(language, code)
    }

    override suspend fun getAssignment(uuid: UUID): Assignment? {
        return assignmentSource.getAssignment(uuid)
    }
}