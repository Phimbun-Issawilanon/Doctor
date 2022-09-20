package org.empowrco.doctor.assignment.presenters

import io.ktor.server.plugins.NotFoundException
import kotlinx.datetime.LocalDateTime
import org.empowrco.doctor.assignment.backend.AssignmentRepository
import org.empowrco.doctor.models.Assignment
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.Feedback
import org.empowrco.doctor.models.NoValidExecutor
import org.empowrco.doctor.models.Success
import org.empowrco.doctor.utils.UnknownException
import org.empowrco.doctor.utils.UnsupportedLanguage
import org.empowrco.doctor.utils.now
import java.util.UUID

interface AssignmentPresenter {
    suspend fun run(request: RunRequest): RunResponse
    suspend fun submit(request: SubmitRequest): SubmitResponse
    suspend fun create(request: CreateAssignmentRequest): CreateAssignmentResponse
}

internal class RealAssignmentPresenter(private val repo: AssignmentRepository) : AssignmentPresenter {
    override suspend fun run(request: RunRequest): RunResponse {
        when (val result = repo.executeCode(request.language, request.code)) {
            is NoValidExecutor -> throw UnsupportedLanguage(result.message)
            is Error -> throw result
            is Success -> {
                val assignment =
                    repo.getAssignment(request.referenceId) ?: throw NotFoundException("Assignemtn not found")
                return RunResponse(
                    output = result.output,
                    expectedOutput = assignment.expectedOutput,
                )
            }
        }
    }

    override suspend fun submit(request: SubmitRequest): SubmitResponse {
        val assignment = repo.getAssignment(request.referenceId) ?: throw NotFoundException("Assignment not found")
        val isFinalAttempt = request.attempt >= assignment.totalAttempts

        if (assignment.totalAttempts > 0 && isFinalAttempt) {
            return SubmitResponse(
                output = assignment.failureMessage,
                expectedOutput = "",
                success = false,
                finalAttempt = isFinalAttempt,
                feedback = ""
            )
        }

        return when (val codeResult = repo.executeCode(request.language, request.code)) {
            is NoValidExecutor -> throw UnsupportedLanguage(codeResult.message)
            is Error -> {
                val error = codeResult.message
                if (assignment.feedback.isEmpty()) {
                    return SubmitResponse(
                        output = error,
                        expectedOutput = assignment.expectedOutput,
                        success = false,
                        finalAttempt = isFinalAttempt,
                        feedback = ""
                    )
                }
                val feedback = getFeedback(assignment, request, error)
                SubmitResponse(
                    output = error,
                    expectedOutput = "",
                    success = false,
                    finalAttempt = isFinalAttempt,
                    feedback = feedback,
                )
            }

            is Success -> {
                if (assignment.expectedOutput == codeResult.output) {
                    SubmitResponse(
                        output = codeResult.output,
                        expectedOutput = assignment.expectedOutput,
                        success = true,
                        finalAttempt = isFinalAttempt,
                        feedback = assignment.successMessage
                    )
                } else {
                    val feedback = getFeedback(assignment, request, codeResult.output)
                    SubmitResponse(
                        output = codeResult.output,
                        expectedOutput = "",
                        success = false,
                        finalAttempt = isFinalAttempt,
                        feedback = feedback
                    )
                }
            }
        }
    }

    private fun getFeedback(
        assignment: Assignment,
        request: SubmitRequest,
        output: String
    ): String {
        val validAttemptFeedback = assignment.feedback.filter {
            it.attempt <= request.attempt
        }.sortedByDescending { it.attempt }
        val feedback = validAttemptFeedback.firstOrNull {
            if (it.regexMatcher.isBlank()) {
                return@firstOrNull false
            }
            it.regex.matches(output)
        } ?: validAttemptFeedback.firstOrNull {
            it.regexMatcher.isBlank()
        }
        return feedback?.feedback ?: ""
    }

    override suspend fun create(request: CreateAssignmentRequest): CreateAssignmentResponse {
        val currentTime = LocalDateTime.now()
        val assignment = Assignment(
            id = UUID.randomUUID(),
            referenceId = request.referenceId,
            failureMessage = request.failureMessage,
            successMessage = request.successMessage,
            instructions = request.instructions,
            expectedOutput = request.expectedOutput,
            solution = request.solution,
            totalAttempts = request.totalAttempts,
            feedback = request.feedback.map {
                Feedback(
                    id = UUID.randomUUID(),
                    feedback = it.feedback,
                    regexMatcher = it.regex,
                    attempt = it.attempt,
                    createdAt = currentTime,
                    lastModifiedAt = currentTime,
                )
            },
            createdAt = currentTime,
            lastModifiedAt = currentTime,
        )
        repo.createAssignment(assignment) ?: throw UnknownException
        return CreateAssignmentResponse
    }
}
