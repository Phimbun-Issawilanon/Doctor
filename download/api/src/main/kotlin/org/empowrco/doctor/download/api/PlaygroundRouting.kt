package org.empowrco.doctor.download.api

import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.routing
import org.empowrco.doctor.download.presenters.DownloadPresenter
import org.empowrco.doctor.download.presenters.DownloadRequest
import org.empowrco.doctor.utils.routing.authPost
import org.koin.ktor.ext.inject

fun Application.playgroundRouting() {
    routing {
        val viewModel: DownloadPresenter by inject()
        authPost("/download") {
            val request = it.receive<DownloadRequest>()
            viewModel.download(request)
        }
    }
}
