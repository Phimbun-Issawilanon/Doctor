package org.empowrco.doctor.command

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields


@OptIn(ExperimentalKotest::class)
class RealCommanderTest : FunSpec() {

    init {
        val commander = RealCommander()
        testCoroutineDispatcher = true

        test("test successful command returns CommandResponse.Success") {
            val response = commander.execute("echo Hello, World")
            response shouldBeEqualToComparingFields CommandResponse.Success("Hello, World")
        }

        test("test invalid command returns CommandResponse.Error") {
            val response = commander.execute("ec ho Hello, World")
            response shouldBeEqualToComparingFields CommandResponse.Error("Cannot run program \"ec\": error=2, No such file or directory")
        }
    }
}