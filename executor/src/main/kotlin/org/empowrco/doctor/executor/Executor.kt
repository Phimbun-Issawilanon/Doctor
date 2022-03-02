package org.empowrco.doctor.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.empowrco.doctor.command.Commander
import org.empowrco.doctor.command.RealCommander
import java.io.File
import java.io.FileWriter

internal interface Executor {
    suspend fun execute(code: String): ExecutorResponse
}

internal class SwiftExecutor(private val commander: Commander = RealCommander()) : Executor {
    override suspend fun execute(code: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            val tempFile = File.createTempFile("swift-exc", ".swift")
            val fileWriter = FileWriter(tempFile)
            fileWriter.use {
                it.appendLine("import Foundation")
                it.appendLine(code)
            }
            val result = commander.execute("swift ${tempFile.absolutePath}")
            tempFile.deleteRecursively()
            ExecutorResponse(result.output)
        }
    }
}