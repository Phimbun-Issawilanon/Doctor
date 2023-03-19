package org.empowrco.doctor.executor.handler

import org.empowrco.doctor.executor.Executor
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.NoValidExecutor

interface CodeHandler {
    suspend fun execute(language: String, code: String, tests: String = ""): ExecutorResponse
}

internal class RealCodeHandler(private val executors: List<Executor>) : CodeHandler {

    override suspend fun execute(language: String, code: String, tests: String): ExecutorResponse {
        for (executor in executors) {
            if (tests.isBlank() && executor.canHandle(language)) {
                return executor.execute(code)
            } else if (tests.isNotBlank() && executor.canHandle(language)) {
                return executor.test(code, tests)
            }
        }
        return NoValidExecutor(language)
    }
}
