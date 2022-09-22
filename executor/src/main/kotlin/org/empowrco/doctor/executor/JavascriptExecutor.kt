package org.empowrco.doctor.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.empowrco.doctor.command.Commander
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.Success
import org.empowrco.doctor.utils.FileUtil

internal class JavascriptExecutor(private val commander: Commander, private val fileUtil: FileUtil) : Executor() {
    override val handledLanguages: Set<String>
        get() = setOf(
            "javascript",
            "text/javascript",
            "application/javascript",
            "application/x-javascript",
            "text/ecmascript",
            "application/ecmascript",
        )

    override suspend fun execute(code: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val tempFile = fileUtil.writeToFile("js-exc", ".js") {
                    it.appendLine(code)
                }
                val result = commander.execute("node ${tempFile.absolutePath}")
                tempFile.deleteRecursively()
                Success(result.output)
            } catch (ex: Exception) {
                Error(ex.message ?: "")
            }
        }
    }
}
