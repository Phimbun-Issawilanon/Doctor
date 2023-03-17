package org.empowrco.doctor.tester.api

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.empowrco.doctor.tester.presenters.TesterPresenter
import org.koin.ktor.ext.inject

fun Application.testerApi() {
    val presenter: TesterPresenter by inject()
    routing {
        post("test") {
            val response = presenter.test(call.receive())
            call.respond(response)
        }
    }
}
