package org.empowrco.doctor.tester.presenters

import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.NoValidExecutor
import org.empowrco.doctor.models.Success
import org.empowrco.doctor.tester.backend.TesterRepository
import org.empowrco.doctor.utils.UnknownException
import org.empowrco.doctor.utils.UnsupportedLanguage

interface TesterPresenter {
    suspend fun test(request: ExecuteTestRequest): ExecuteTestsResponse
}

internal class RealTesterPresenter(
    private val repo: TesterRepository
) : TesterPresenter {
    override suspend fun test(request: ExecuteTestRequest): ExecuteTestsResponse {
        return when (val result = repo.test(request.language, request.code, request.tests)) {
            is NoValidExecutor -> throw UnsupportedLanguage(result.message)
            is Error -> throw result
            is Success -> {
                ExecuteTestsResponse(
                    output = result.output,
                )
            }

            else -> throw UnknownException
        }
    }
}
