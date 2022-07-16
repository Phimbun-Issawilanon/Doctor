package org.empowrco.doctor.api.assignment

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.assignmentRouting() {
    val presenter: AssignmentPresenter by inject()
    routing {
        post("/run") {
            val request = call.receive<RunRequest>()
            val response = presenter.run(request)
            call.respond(response)
        }
    }
}