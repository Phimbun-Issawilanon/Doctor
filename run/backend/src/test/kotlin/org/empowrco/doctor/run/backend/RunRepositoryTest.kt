package org.empowrco.doctor.run.backend

import kotlinx.coroutines.runBlocking
import org.empowrco.doctor.executor.fakes.handler.FakeCodeHandler
import org.empowrco.doctor.models.Success
import kotlin.test.Test
import kotlin.test.assertEquals

class RunRepositoryTest {
    private val codeHandler = FakeCodeHandler()
    private val repo = RealRunRepository(codeHandler)


    @Test
    fun execute() = runBlocking {
        val executorResponse = Success("success", true)
        codeHandler.executorResponses.add(executorResponse)
        val result = repo.executeCode("lang", "code")
        assertEquals(executorResponse, result)
    }
}
