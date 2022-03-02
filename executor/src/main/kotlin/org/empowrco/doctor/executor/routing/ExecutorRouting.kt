package org.empowrco.doctor.executor.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.executor() {
    routing {
        val viewModel = ExecutorViewModel()
        post("/run") {
            val request = call.receive<RunRequest>()
            val code = request.code
            if (code.isBlank()) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val response = viewModel.run(code)
            call.respond(response)
        }

        post("/submit") {
            val request = call.receive<SubmitRequest>()

        }
    }
}