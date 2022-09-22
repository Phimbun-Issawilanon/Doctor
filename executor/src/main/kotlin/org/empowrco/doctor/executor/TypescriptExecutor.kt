package org.empowrco.doctor.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.empowrco.doctor.command.CommandResponse
import org.empowrco.doctor.command.Commander
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.Success
import java.io.File
import java.io.FileWriter

internal class TypescriptExecutor(private val commander: Commander) : Executor() {
    override val handledLanguages: Set<String>
        get() = setOf("typescript", "text/typescript", "application/typescript")

    override suspend fun execute(code: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val tempFile = File.createTempFile("ts-exc", ".ts")
                val fileWriter = FileWriter(tempFile)
                fileWriter.use {
                    it.appendLine(code)
                }
                val tscResult = commander.execute("tsc ${tempFile.absolutePath}")
                if (tscResult is CommandResponse.Error) {
                    return@withContext Error(tscResult.output)
                }
                val jsFilePath = tempFile.absolutePath.removeSuffix(".ts") + ".js"
                val result = commander.execute("node $jsFilePath")
                tempFile.deleteRecursively()
                Success(result.output)
            } catch (ex: Exception) {
                Error(ex.message ?: "")
            }
        }
    }
}
