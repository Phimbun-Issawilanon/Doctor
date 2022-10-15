package org.empowrco.doctor.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.empowrco.doctor.command.CommandResponse
import org.empowrco.doctor.command.Commander
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.Success
import org.empowrco.doctor.utils.files.FileUtil
import java.io.File

internal class RustExecutor(private val commander: Commander, private val fileUtil: FileUtil) : Executor() {
    override val handledLanguages = setOf("rust", "text/x-rustsrc")
    override suspend fun execute(code: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val addMainFunction = !code.contains(Regex("fn main\\([a-zA-Z: <>]*\\)"))
                val tempFile = fileUtil.writeToFile("rust-ex", ".rs", true) {
                    if (addMainFunction) {
                        it.appendLine("fn main() {")
                    }
                    it.appendLine(code)
                    if (addMainFunction) {
                        it.appendLine("}")
                    }
                }
                val commandResponse = commander.execute("rustc ${tempFile}")
                if (commandResponse is CommandResponse.Error) {
                    return@withContext Error(commandResponse.output)
                }
                val filename = tempFile.nameWithoutExtension
                val executeResult = commander.execute("./${tempFile.nameWithoutExtension}")
                tempFile.deleteRecursively()
                File(filename).deleteRecursively()
                Success(executeResult.output, executeResult is CommandResponse.Error)
            } catch (ex: Exception) {
                Error(ex.message ?: "")
            }
        }
    }
}
