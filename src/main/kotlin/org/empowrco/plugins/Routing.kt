package org.empowrco.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.empowrco.doctor.download.api.downloadApi
import org.empowrco.doctor.run.api.runApi
import org.empowrco.doctor.tester.api.testerApi

fun Application.configureRouting() {
    downloadApi()
    runApi()
    testerApi()
    routing {
        get("/health") {
            call.respond("Hello, World")
        }
    }
}
