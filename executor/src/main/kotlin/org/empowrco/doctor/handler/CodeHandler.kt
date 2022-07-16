package org.empowrco.doctor.handler

import org.empowrco.doctor.executor.Executor
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.NoValidExecutor

interface CodeHandler {
    suspend fun execute(language: String, code: String): ExecutorResponse
}

internal class RealCodeHandler(private val executors: List<Executor>) : CodeHandler {

    override suspend fun execute(language: String, code: String): ExecutorResponse {
        for (executor in executors) {
            if (executor.canHandle(language)) {
                return executor.execute(code)
            }
        }
        return NoValidExecutor(language)
    }
}