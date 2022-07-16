package org.empowrco.doctor.api.assignment

import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.Success

internal interface AssignmentPresenter {
    suspend fun run(request: RunRequest): RunResponse
}

internal class RealAssignmentPresenter(private val repo: AssignmentRepository) : AssignmentPresenter {
    override suspend fun run(request: RunRequest): RunResponse {
        val result = repo.executeCode(request.language, request.code)
        when (result) {
            is Error -> throw result
            is Success -> return RunResponse(result.output)
        }
    }
}