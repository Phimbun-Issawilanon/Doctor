package org.empowrco.doctor.assignment.backend

import org.empowrco.doctor.handler.CodeHandler
import org.empowrco.doctor.models.Assignment
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.sources.AssignmentSource

interface AssignmentRepository {
    suspend fun executeCode(language: String, code: String): ExecutorResponse
    suspend fun getAssignment(referenceId: String): Assignment?
    suspend fun createAssignment(assignment: Assignment): Assignment?
}

internal class RealAssignmentRepository(
    private val handler: CodeHandler,
    private val assignmentSource: AssignmentSource,
) : AssignmentRepository {
    override suspend fun executeCode(language: String, code: String): ExecutorResponse {
        return handler.execute(language, code)
    }

    override suspend fun getAssignment(referenceId: String): Assignment? {
        return assignmentSource.getAssignmentByReferenceId(referenceId)
    }

    override suspend fun createAssignment(assignment: Assignment): Assignment? {
        return assignmentSource.createAssignment(assignment)?.let { assignmentSource.getAssignment(it) }
    }
}