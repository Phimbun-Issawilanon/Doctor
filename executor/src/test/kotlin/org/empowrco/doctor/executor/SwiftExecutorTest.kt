package org.empowrco.doctor.executor

import kotlinx.coroutines.runBlocking
import org.empowrco.doctor.command.CommandResponse
import org.empowrco.doctor.command.fakes.FakeCommander
import org.empowrco.doctor.models.Success
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SwiftExecutorTest {
    private val commander = FakeCommander()
    private val executor = SwiftExecutor(commander = commander)

    @Test
    fun success() = runBlocking {
        val code = "print(\"Hello, World\")"
        commander.responses.add(CommandResponse.Success("Hello, World"))
        val response = executor.execute(code)
        assertEquals(response, Success("Hello, World"))
    }
}
