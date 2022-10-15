package org.empowrco.doctor.run.backend.fakes

import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.run.backend.RunRepository

class FakeRunRepo : RunRepository {

    val executorResponses = mutableListOf<ExecutorResponse>()

    override suspend fun executeCode(language: String, code: String): ExecutorResponse {
        return executorResponses.last()
    }
}
