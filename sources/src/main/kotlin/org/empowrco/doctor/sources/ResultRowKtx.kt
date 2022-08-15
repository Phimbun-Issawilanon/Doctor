package org.empowrco.doctor.sources

import org.empowrco.doctor.db.Assignments
import org.empowrco.doctor.db.Feedbacks
import org.empowrco.doctor.models.Assignment
import org.empowrco.doctor.models.Feedback
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import java.util.UUID

internal fun ResultRow.toFeedback(): Feedback {
    return Feedback(
        id = this[Feedbacks.id].value,
        attempt = this[Feedbacks.attempt],
        regexMatcher = this[Feedbacks.regexMatcher],
        feedback = this[Feedbacks.feedback],
        createdAt = this[Feedbacks.createdAt],
        lastModifiedAt = this[Feedbacks.lastModifiedAt]
    )
}

internal fun Feedbacks.insert(feedback: List<Feedback>, assignmentId: UUID): List<Feedback> {
    return batchInsert(feedback) {
        this[Feedbacks.feedback] = it.feedback
        this[Feedbacks.regexMatcher] = it.regexMatcher
        this[Feedbacks.attempt] = it.attempt
        this[Feedbacks.assignmentId] = assignmentId
        this[Feedbacks.createdAt] = it.createdAt
        this[Feedbacks.lastModifiedAt] = it.lastModifiedAt
    }.map { it.toFeedback() }
}

internal fun Assignments.insert(assignment: Assignment): UUID {
    return insertAndGetId {
        it.build(assignment)
    }.value
}

internal fun Assignments.update(assignment: Assignment): Int {
    return update({ Assignments.id eq assignment.id }) {
        it.build(assignment, true)
    }
}

internal fun UpdateBuilder<*>.build(assignment: Assignment, update: Boolean = false) {
    this[Assignments.referenceId] = assignment.referenceId
    this[Assignments.expectedOutput] = assignment.expectedOutput
    this[Assignments.successMessage] = assignment.successMessage
    this[Assignments.solution] = assignment.solution
    this[Assignments.failureMessage] = assignment.failureMessage
    this[Assignments.instructions] = assignment.instructions
    this[Assignments.totalAttempts] = assignment.totalAttempts
    if (!update) {
        this[Assignments.createdAt] = assignment.createdAt
    }
    this[Assignments.lastModifiedAt] = assignment.lastModifiedAt
}

internal fun ResultRow.toAssignment(): Assignment {
    val id = this[Assignments.id].value
    val feedback = Feedbacks.select { Feedbacks.assignmentId eq id }.map { it.toFeedback() }
    return Assignment(
        id = id,
        referenceId = this[Assignments.referenceId],
        expectedOutput = this[Assignments.expectedOutput],
        feedback = feedback,
        createdAt = this[Assignments.createdAt],
        lastModifiedAt = this[Assignments.lastModifiedAt],
        failureMessage = this[Assignments.failureMessage],
        successMessage = this[Assignments.successMessage],
        instructions = this[Assignments.instructions],
        solution = this[Assignments.solution],
        totalAttempts = this[Assignments.totalAttempts],
    )
}