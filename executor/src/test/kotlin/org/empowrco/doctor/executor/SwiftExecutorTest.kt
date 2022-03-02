package org.empowrco.doctor.executor

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import org.empowrco.doctor.command.CommandResponse
import org.empowrco.doctor.command.fakes.FakeCommander

class SwiftExecutorTest: FunSpec() {
    init {
        val commander = FakeCommander()
        val executor = SwiftExecutor(commander = commander)

        test("success") {
            val code = "print(\"Hello, World\")"
            commander.responses.add(CommandResponse.Success("Hello, World"))
            val response = executor.execute(code)
            response shouldBeEqualToComparingFields ExecutorResponse("Hello, World")
        }
    }
}