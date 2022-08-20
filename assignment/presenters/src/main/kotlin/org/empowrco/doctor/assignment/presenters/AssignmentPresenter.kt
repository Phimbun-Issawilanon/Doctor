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
        val result = repo.executeCode(request.language, request.code)
        when (result) {
            is NoValidExecutor -> throw UnsupportedLanguage(result.message)
            is Error -> throw result
            is Success -> return RunResponse(result.output)
        }
    }

    override suspend fun submit(request: SubmitRequest): SubmitResponse {
        val assignment = repo.getAssignment(request.referenceId) ?: throw NotFoundException("Assignment not found")

        if (assignment.totalAttempts > 0 && assignment.totalAttempts < request.attempt) {
            return SubmitResponse(assignment.failureMessage, "", false)
        }

        val codeResult = repo.executeCode(request.language, request.code)
        return when (codeResult) {
            is NoValidExecutor -> throw UnsupportedLanguage(codeResult.message)
            is Error -> {
                val error = codeResult.message
                if (assignment.feedback.isEmpty()) {
                    return SubmitResponse(error, "", false)
                }
                val feedback = getFeedback(assignment, request, error)
                SubmitResponse(error, feedback, false)
            }

            is Success -> {
                if (assignment.expectedOutput == codeResult.output) {
                    SubmitResponse(codeResult.output, assignment.successMessage, true)
                } else {
                    val feedback = getFeedback(assignment, request, codeResult.output)
                    SubmitResponse(codeResult.output, feedback, false)
                }
            }
        }
    }

    private fun getFeedback(
        assignment: Assignment,
        request: SubmitRequest,
        output: String
    ): String {
        val sortedFeedback = assignment.feedback.sortedByDescending { it.attempt }
        val topLimit = sortedFeedback.firstOrNull {
            it.attempt < request.attempt
        } ?: return ""
        val topLimitIndex = sortedFeedback.indexOf(topLimit)
        val bottomFeedback = sortedFeedback.filterIndexed { index, _ ->
            index > topLimitIndex
        }
        val validAttemptFeedback = if (bottomFeedback.isNotEmpty()) {
            val bottomLimit = bottomFeedback.firstOrNull {
                it.attempt > request.attempt
            }
            val bottomLimitIndex = bottomLimit?.let { sortedFeedback.indexOf(bottomLimit) } ?: sortedFeedback.lastIndex
            sortedFeedback.subList(topLimitIndex, bottomLimitIndex)
        } else {
            sortedFeedback
        }
        val feedback = validAttemptFeedback.find {
            if (it.regexMatcher.isBlank()) {
                return@find false
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
                    regexMatcher = it.regexMatcher,
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
