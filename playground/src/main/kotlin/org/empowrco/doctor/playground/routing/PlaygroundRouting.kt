package org.empowrco.doctor.playground.routing

import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.routing
import org.empowrco.doctor.utils.routing.authPost

fun Application.playgroundRouting() {
    routing {
        val viewModel = DownloadViewModel()
        authPost("/download") {
            val request = it.receive<DownloadRequest>()
            val response = viewModel.download(request)
            response
        }
    }
}
