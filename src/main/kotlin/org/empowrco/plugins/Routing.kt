package org.empowrco.plugins

import io.ktor.server.application.Application
import org.empowrco.doctor.api.assignment.assignmentRouting
import org.empowrco.doctor.playground.routing.playgroundRouting

fun Application.configureRouting() {
    playgroundRouting()
    assignmentRouting()
}
