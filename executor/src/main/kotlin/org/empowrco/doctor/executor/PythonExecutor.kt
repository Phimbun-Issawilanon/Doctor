package org.empowrco.doctor.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.empowrco.doctor.command.Commander
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.Success
import java.io.File
import java.io.FileWriter

internal class PythonExecutor(private val commander: Commander) : Executor() {
    override val handledLanguages: Set<String>
        get() = setOf("python", "text/x-python")

    override suspend fun execute(code: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val tempFile = File.createTempFile("python-exc", ".py")
                val fileWriter = FileWriter(tempFile)
                fileWriter.use {
                    it.appendLine(code)
                }
                val result = commander.execute("python3 ${tempFile.absolutePath}")
                tempFile.deleteRecursively()
                Success(result.output)
            } catch (ex: Exception) {
                Error(ex.message ?: "")
            }
        }
    }
}
