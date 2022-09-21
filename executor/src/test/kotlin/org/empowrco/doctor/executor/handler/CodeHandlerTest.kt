package org.empowrco.doctor.executor.handler

import kotlinx.coroutines.runBlocking
import org.empowrco.doctor.executor.Executor
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.NoValidExecutor
import org.empowrco.doctor.models.Success
import kotlin.test.Test
import kotlin.test.assertEquals

class CodeHandlerTest {

    private val executors = listOf(swiftExecutor, kotlinExecutor)
    private val handler = RealCodeHandler(executors)


    @Test
    fun execute() = runBlocking {
        val response = Success("code")
        swiftExecutor.responses.add(response)
        val result = handler.execute("swift", "")
        assertEquals(response, result)
    }

    @Test
    fun executeNonFirst() = runBlocking {
        swiftExecutor.responses.add(Success("swift"))
        val response = Success("kotlin")
        kotlinExecutor.responses.add(Success("kotlin"))
        val result = handler.execute("kotlin", "")
        assertEquals(response, result)
    }

    @Test
    fun executeUnsupportedLanguage() = runBlocking {
        val response = Success("code")
        swiftExecutor.responses.add(response)
        val result = handler.execute("javascript", "")
        assertEquals(NoValidExecutor("javascript"), result)
    }

    companion object {
        val swiftExecutor = FakeExecutor("swift")
        val kotlinExecutor = FakeExecutor("kotlin")
    }


    class FakeExecutor(private val language: String) : Executor() {
        val responses = mutableListOf<ExecutorResponse>()
        override val handledLanguages: Set<String>
            get() = setOf(language)

        override suspend fun execute(code: String): ExecutorResponse {
            return responses.first()
        }

    }
}
