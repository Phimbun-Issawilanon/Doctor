package org.empowrco.doctor.download.presenters

import io.ktor.server.plugins.BadRequestException
import org.empowrco.doctor.download.backend.handler.DownloadRepository
import java.io.File

interface DownloadPresenter {
    suspend fun download(request: DownloadRequest): File
}

internal class RealDownloadPresenter(private val repo: DownloadRepository) : DownloadPresenter {
    override suspend fun download(request: DownloadRequest): File {
        if (request.code.isBlank()) {
            throw BadRequestException("Code should not be blank")
        }
        if (request.language.isBlank()) {
            throw BadRequestException("Language should not be blank")
        }
        val downloadPath = repo.generate(request.language, request.code)
        return downloadPath.toFile()
    }
}
