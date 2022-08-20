package org.empowrco.doctor.playground.routing

import io.ktor.server.plugins.BadRequestException
import org.empowrco.doctor.playground.generator.Playground
import org.empowrco.doctor.playground.generator.RealPlayground
import java.io.File

internal class DownloadViewModel(private val playground: Playground = RealPlayground()) {
    suspend fun download(request: DownloadRequest): File {
        if (request.code.isBlank()) {
            throw BadRequestException("code should not be blank")
        }
        val downloadPath = playground.generate(request.code)
        return downloadPath.toFile()
    }
}
