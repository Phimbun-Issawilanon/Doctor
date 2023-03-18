package org.empowrco.doctor.download.api

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.empowrco.doctor.download.presenters.DownloadPresenter
import org.empowrco.doctor.download.presenters.DownloadRequest
import org.koin.ktor.ext.inject

fun Application.downloadApi() {
    routing {
        val viewModel: DownloadPresenter by inject()
        post("/download") {
            val request = call.receive<DownloadRequest>()
            viewModel.download(request)
        }
    }
}
