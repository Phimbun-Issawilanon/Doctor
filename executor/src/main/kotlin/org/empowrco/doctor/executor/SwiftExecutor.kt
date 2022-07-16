package org.empowrco.doctor.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.empowrco.doctor.command.Commander
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.Success
import java.io.File
import java.io.FileWriter

internal class SwiftExecutor(private val commander: Commander) : Executor() {
    override val handledLanguages = setOf("swift", "text/x-swift")
    override suspend fun execute(code: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val tempFile = File.createTempFile("swift-exc", ".swift")
                val fileWriter = FileWriter(tempFile)
                fileWriter.use {
                    it.appendLine("import Foundation")
                    it.appendLine(code)
                }
                val result = commander.execute("swift ${tempFile.absolutePath}")
                tempFile.deleteRecursively()
                Success(result.output)
            } catch (ex: Exception) {
                Error(ex.message)
            }
        }
    }
}