package org.empowrco.doctor.run.presenters

import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.NoValidExecutor
import org.empowrco.doctor.models.Success
import org.empowrco.doctor.run.backend.RunRepository
import org.empowrco.doctor.utils.UnknownException
import org.empowrco.doctor.utils.UnsupportedLanguage

interface RunPresenter {
    suspend fun run(request: RunRequest): RunResponse
}

internal class RealRunPresenter(
    private val repo: RunRepository,
) : RunPresenter {
    override suspend fun run(request: RunRequest): RunResponse {
        when (val result = repo.executeCode(request.language, request.code)) {
            is NoValidExecutor -> throw UnsupportedLanguage(result.message)
            is Error -> throw result
            is Success -> {
                return RunResponse(
                    output = result.output,
                    success = !result.isStacktraceError,
                )
            }

            else -> throw UnknownException
        }
    }
}
