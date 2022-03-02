package org.empowrco.doctor.executor.routing

import org.empowrco.doctor.executor.Executor
import org.empowrco.doctor.executor.ExecutorResponse
import org.empowrco.doctor.executor.SwiftExecutor

internal interface ExecutorRepository {
    suspend fun execute(code: String): ExecutorResponse
}

internal class RealExecutorRepository(private val executor: Executor = SwiftExecutor()): ExecutorRepository {
    override suspend fun execute(code: String): ExecutorResponse {
        return executor.execute(code)
    }
}