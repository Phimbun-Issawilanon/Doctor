package org.empowrco.doctor.assignment.api

import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.routing
import org.empowrco.doctor.assignment.presenters.AssignmentPresenter
import org.empowrco.doctor.utils.routing.authPost
import org.koin.ktor.ext.inject

fun Application.assignmentRouting() {
    val presenter: AssignmentPresenter by inject()
    routing {
        authPost("/run") {
            presenter.run(it.receive())
        }
        authPost("/submit") {
            presenter.submit(it.receive())
        }
        authPost("/assignment") {
            presenter.create(it.receive())
        }
    }
}
