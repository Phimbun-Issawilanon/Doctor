package org.empowrco.doctor.playground.routing

import org.empowrco.doctor.playground.generator.Playground
import org.empowrco.doctor.playground.generator.RealPlayground
import java.io.File

internal class DownloadViewModel(private val playground: Playground = RealPlayground()) {
    suspend fun download(code: String): File {
        val downloadPath = playground.generate(code)
        return downloadPath.toFile()
    }
}