package org.empowrco.doctor.run.api

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.empowrco.doctor.run.presenters.RunPresenter
import org.koin.ktor.ext.inject

fun Application.runApi() {
    val presenter: RunPresenter by inject()
    routing {
        post("/run") {
            val response = presenter.run(call.receive())
            call.respond(response)
        }
    }
}
