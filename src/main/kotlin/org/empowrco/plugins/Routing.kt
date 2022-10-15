package org.empowrco.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.empowrco.doctor.download.api.playgroundRouting
import org.empowrco.doctor.run.api.runRouting

fun Application.configureRouting() {
    playgroundRouting()
    runRouting()
    routing {
        get("/health") {
            call.respond("Hello, World")
        }
    }
}
