package org.empowrco.doctor.executor.fakes.handler

import org.empowrco.doctor.executor.handler.CodeHandler
import org.empowrco.doctor.models.ExecutorResponse

class FakeCodeHandler : CodeHandler {
    val executorResponses = mutableListOf<ExecutorResponse>()
    override suspend fun execute(language: String, code: String): ExecutorResponse {
        return executorResponses.removeFirst()
    }
}
