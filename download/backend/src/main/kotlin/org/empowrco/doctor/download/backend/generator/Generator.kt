package org.empowrco.doctor.download.backend.generator

import java.nio.file.Path

internal abstract class Generator {
    internal abstract val handlesLanguages: Set<String>
    internal abstract suspend fun generate(code: String): Path
    internal fun canHandle(language: String): Boolean {
        return handlesLanguages.contains(language)
    }
}
