package org.empowrco.plugins

import io.ktor.server.application.Application
import org.empowrco.doctor.executor.routing.executor
import org.empowrco.doctor.playground.routing.playground

fun Application.configureRouting() {
    playground()
    executor()
}
