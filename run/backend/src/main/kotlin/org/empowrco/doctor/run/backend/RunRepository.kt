package org.empowrco.doctor.run.backend

import org.empowrco.doctor.executor.handler.CodeHandler
import org.empowrco.doctor.models.ExecutorResponse

interface RunRepository {
    suspend fun executeCode(language: String, code: String): ExecutorResponse
}

internal class RealRunRepository(
    private val handler: CodeHandler,
) : RunRepository {
    override suspend fun executeCode(language: String, code: String): ExecutorResponse {
        return handler.execute(language, code)
    }
}
