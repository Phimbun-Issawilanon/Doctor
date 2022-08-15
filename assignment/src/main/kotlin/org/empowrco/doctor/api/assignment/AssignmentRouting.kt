package org.empowrco.doctor.api.assignment

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import org.koin.ktor.ext.inject

fun Application.assignmentRouting() {
    val presenter: AssignmentPresenter by inject()
    routing {
        post("/run") {
            val bodyArray = call.receiveText().toByteArray(charset = Charsets.UTF_8)
            val bodyString = String(bodyArray)
            val json = Json.parseToJsonElement(bodyString)
            val request = Json.decodeFromJsonElement<RunRequest>(json)
            val response = presenter.run(request)
            call.respond(response)
        }
        post("/submit") {
            // TODO add your code to handle assignment submissions here.
        }
        post("/assignment") {

        }
    }
}