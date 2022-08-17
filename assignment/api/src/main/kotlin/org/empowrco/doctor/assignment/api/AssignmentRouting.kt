package org.empowrco.doctor.assignment.api

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.empowrco.doctor.assignment.presenters.AssignmentPresenter
import org.koin.ktor.ext.inject

fun Application.assignmentRouting() {
    val presenter: AssignmentPresenter by inject()
    routing {
        post("/run") {
            val response = presenter.run(call.receive())
            call.respond(response)
        }
        post("/submit") {
            val response = presenter.submit(call.receive())
            call.respond(response)
        }
        post("/assignment") {
            val response = presenter.create(call.receive())
            call.respond(response)
        }
    }
}