package org.empowrco.doctor.executor.routing

internal class ExecutorViewModel(private val repository: ExecutorRepository = RealExecutorRepository()) {

    suspend fun run(code: String): ExecutorResult {
        val response = repository.execute(code)
        return ExecutorResult(
            output = response.output,
            code = code,
        )
    }
}