package org.empowrco.doctor.download.backend.handler

import org.empowrco.doctor.download.backend.generator.Generator
import java.nio.file.Path

interface DownloadRepository {
    suspend fun generate(language: String, code: String): Path
}

internal class RealDownloadRepository(private val generators: List<Generator>) : DownloadRepository {
    override suspend fun generate(language: String, code: String): Path {
        for (generator in generators) {
            if (generator.canHandle(language)) {
                return generator.generate(code)
            }
        }
        throw RuntimeException("No generator for $language")
    }
}
