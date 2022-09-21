package org.empowrco.doctor.assignment.backend

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import org.empowrco.doctor.executor.fakes.handler.FakeCodeHandler
import org.empowrco.doctor.models.Assignment
import org.empowrco.doctor.models.Success
import org.empowrco.doctor.sources.fakes.FakeAssignmentSource
import org.empowrco.doctor.utils.now
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class AssignmentRepositoryTest {
    private val codeHandler = FakeCodeHandler()
    private val assignmentSource = FakeAssignmentSource()
    private val repo = RealAssignmentRepository(codeHandler, assignmentSource)


    @Test
    fun execute() = runBlocking {
        val executorResponse = Success("success")
        codeHandler.executorResponses.add(executorResponse)
        val result = repo.executeCode("lang", "code")
        assertEquals(executorResponse, result)
    }

    @Test
    fun getAssignment() = runBlocking {
        val assignment = Assignment(
            id = UUID.randomUUID(),
            expectedOutput = "e_o",
            feedback = emptyList(),
            lastModifiedAt = LocalDateTime.Companion.now(),
            createdAt = LocalDateTime.Companion.now(),
            successMessage = "success",
            failureMessage = "failure",
            referenceId = "reference",
            totalAttempts = 4,
            instructions = "instructions",
            solution = "solution"
        )
        assignmentSource.assignments.add(assignment)
        val result = repo.getAssignment("reference")
        assertEquals(assignment, result)
    }

    @Test
    fun createAssignment() = runBlocking {
        val assignment = Assignment(
            id = UUID.randomUUID(),
            expectedOutput = "e_o",
            feedback = emptyList(),
            lastModifiedAt = LocalDateTime.Companion.now(),
            createdAt = LocalDateTime.Companion.now(),
            successMessage = "success",
            failureMessage = "failure",
            referenceId = "reference",
            totalAttempts = 4,
            instructions = "instructions",
            solution = "solution"
        )
        assignmentSource.assignments.add(assignment)
        val result = repo.createAssignment(assignment)
        assertEquals(assignment, result)
    }

}
