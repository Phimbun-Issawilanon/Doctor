package org.empowrco.doctor.sources

import org.empowrco.doctor.db.Assignments
import org.empowrco.doctor.db.Feedbacks
import org.empowrco.doctor.models.Assignment
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import java.util.UUID

interface AssignmentSource {
    suspend fun getAssignment(id: UUID): Assignment?
    suspend fun getAssignmentByReferenceId(id: String): Assignment?
    suspend fun createAssignment(assignment: Assignment): UUID?
    suspend fun deleteAssignment(id: UUID): Boolean
    suspend fun updateAssignment(assignment: Assignment): Boolean
}

internal class RealAssignmentSource : AssignmentSource {
    override suspend fun getAssignment(id: UUID): Assignment? = dbQuery {
        Assignments.select { Assignments.id eq id }.limit(1).map { it.toAssignment() }.firstOrNull()
    }

    override suspend fun getAssignmentByReferenceId(id: String): Assignment? = dbQuery {
        Assignments.select { Assignments.referenceId eq id }.limit(1).map { it.toAssignment() }.firstOrNull()
    }

    override suspend fun createAssignment(assignment: Assignment): UUID? = dbQuery {
        val id = Assignments.insert(assignment)
        Feedbacks.insert(assignment.feedback, id)
        id
    }

    override suspend fun deleteAssignment(id: UUID): Boolean = dbQuery {
        Assignments.deleteWhere { Assignments.id eq id } > 0
    }

    override suspend fun updateAssignment(assignment: Assignment): Boolean = dbQuery {
        val result = Assignments.update(assignment)

        val feedbackIds = Feedbacks.select { Feedbacks.assignmentId eq assignment.id }
            .map { it[Feedbacks.id].value }
        Feedbacks.deleteWhere { Feedbacks.id inList feedbackIds }
        Feedbacks.insert(assignment.feedback, assignment.id)

        result > 0
    }
}