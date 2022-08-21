package org.empowrco.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.empowrco.doctor.assignment.api.assignmentRouting
import org.empowrco.doctor.download.api.playgroundRouting

fun Application.configureRouting() {
    playgroundRouting()
    assignmentRouting()
    routing {
        get("/health") {
            call.respond("Hello, World")
        }
    }
}
