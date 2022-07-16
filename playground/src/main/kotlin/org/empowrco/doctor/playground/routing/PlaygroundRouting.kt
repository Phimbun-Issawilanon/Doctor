package org.empowrco.doctor.playground.routing

import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.empowrco.doctor.routing.utils.handleAuthenticatedPost

fun Application.playgroundRouting() {
    routing {
        val viewModel = DownloadViewModel()
        post("/download") {
            handleAuthenticatedPost {
                val request = call.receive<DownloadRequest>()
                val code = request.code
                if (code.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val response = viewModel.download(code)
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, response.name)
                        .toString()
                )
                response
            }

        }
    }
}