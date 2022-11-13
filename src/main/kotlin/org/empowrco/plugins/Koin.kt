package org.empowrco.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.empowrco.doctor.command.commandModule
import org.empowrco.doctor.executor.executorModules
import org.empowrco.doctor.run.runModule
import org.empowrco.doctor.tester.testModules
import org.empowrco.doctor.utils.utilsModule
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(executorModules + commandModule + utilsModule + runModule + testModules)
    }
}
