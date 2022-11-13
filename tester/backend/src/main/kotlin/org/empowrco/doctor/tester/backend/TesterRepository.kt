package org.empowrco.doctor.tester.backend

import org.empowrco.doctor.executor.handler.CodeHandler
import org.empowrco.doctor.models.ExecutorResponse

interface TesterRepository {
    suspend fun test(language: String, code: String, tests: String): ExecutorResponse
}

internal class RealTesterRepository(
    private val handler: CodeHandler,
) : TesterRepository {

    override suspend fun test(language: String, code: String, tests: String): ExecutorResponse {
        return handler.execute(language, code, tests)
    }
}
