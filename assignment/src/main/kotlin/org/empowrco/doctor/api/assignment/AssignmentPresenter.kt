package org.empowrco.doctor.api.assignment

import io.ktor.server.plugins.NotFoundException
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.Success
import java.util.UUID

internal interface AssignmentPresenter {
    suspend fun run(request: RunRequest): RunResponse
    suspend fun submit(request: SubmitRequest): SubmitResponse
    suspend fun create(request: CreateAssignmentRequest): CreateAssignmentResponse
}

internal class RealAssignmentPresenter(private val repo: AssignmentRepository) : AssignmentPresenter {
    override suspend fun run(request: RunRequest): RunResponse {
        val result = repo.executeCode(request.language, request.code)
        when (result) {
            is Error -> throw result
            is Success -> return RunResponse(result.output)
        }
    }

    override suspend fun submit(request: SubmitRequest): SubmitResponse {
        val assignmentId = UUID.fromString(request.assignmentId)
        val assignment = repo.getAssignment(assignmentId) ?: throw NotFoundException("Assigment not found")

        if (assignment.totalAttempts > 0 && assignment.totalAttempts < request.attempt) {
            return SubmitResponse("No more submission attempts allowed", "")
        }

        val codeResult = repo.executeCode(request.language, request.code)
        return when (codeResult) {
            is Error -> {
                val error = codeResult.message
                if (assignment.feedback.isEmpty()) {
                    return SubmitResponse(error, "")
                }
                val sortedFeedback = assignment.feedback.sortedByDescending { it.attempt }
                val topLimit = sortedFeedback.first {
                    it.attempt < request.attempt
                }
                val topLimitIndex = sortedFeedback.indexOf(topLimit)
                sortedFeedback.filterIndexed { index, _ ->
                    index > topLimitIndex
                }
                val bottomLimit = sortedFeedback.first {
                    it.attempt > request.attempt
                }
                val bottomLimitIndex = sortedFeedback.indexOf(bottomLimit)
                val validAttemptFeedback = sortedFeedback.subList(topLimitIndex, bottomLimitIndex)
                val feedback = validAttemptFeedback.find {
                    if (it.regexMatcher.isBlank()) {
                        return@find false
                    }
                    it.regex.matches(error)
                } ?: validAttemptFeedback.firstOrNull()
                SubmitResponse(error, feedback?.feedback ?: "")
            }

            is Success -> {
                SubmitResponse(codeResult.output, "")
            }
        }
    }
}