package org.empowrco.doctor.run.api

import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.routing
import org.empowrco.doctor.run.presenters.RunPresenter
import org.empowrco.doctor.utils.routing.authPost
import org.koin.ktor.ext.inject

fun Application.runApi() {
    val presenter: RunPresenter by inject()
    routing {
        authPost("/run") {
            presenter.run(it.receive())
        }
    }
}
