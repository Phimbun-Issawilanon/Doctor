package org.empowrco.doctor.command.fakes

import org.empowrco.doctor.command.CommandResponse
import org.empowrco.doctor.command.Commander

class FakeCommander: Commander {
    val responses = ArrayDeque<CommandResponse>()
    override suspend fun execute(command: String): CommandResponse {
        return responses.removeLast()
    }
}
