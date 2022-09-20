package org.empowrco.doctor.sources.fakes

import org.empowrco.doctor.models.Assignment
import org.empowrco.doctor.sources.AssignmentSource
import java.util.UUID

class FakeAssignmentSource : AssignmentSource {

    val assignments = mutableListOf<Assignment>()

    override suspend fun getAssignment(id: UUID): Assignment? {
        return assignments.find { it.id == id }
    }

    override suspend fun getAssignmentByReferenceId(id: String): Assignment? {
        return assignments.find { it.referenceId == id }
    }

    override suspend fun createAssignment(assignment: Assignment): UUID? {
        assignments.add(assignment)
        return assignment.id
    }

    override suspend fun deleteAssignment(id: UUID): Boolean {
        return assignments.removeIf { it.id == id }
    }

    override suspend fun updateAssignment(assignment: Assignment): Boolean {
        val result = assignments.removeIf {
            assignment.id == it.id
        }
        if (result) {
            assignments.add(assignment)
        }
        return result
    }
}
