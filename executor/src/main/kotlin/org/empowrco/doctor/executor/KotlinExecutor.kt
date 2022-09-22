package org.empowrco.doctor.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.empowrco.doctor.command.Commander
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.Success
import org.empowrco.doctor.utils.FileUtil

internal class KotlinExecutor(private val commander: Commander, private val fileUtil: FileUtil) : Executor() {
    override val handledLanguages = setOf("kotlin", "text/x-kotlin")
    override suspend fun execute(code: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val addMainFunction = !code.contains(Regex("main\\([a-zA-Z: <>]*\\)"))
                val tempFile = fileUtil.writeToFile("kotlin-exc", ".kt") {
                    if (addMainFunction) {
                        it.appendLine("fun main() {")
                    }
                    it.appendLine(code)
                    if (addMainFunction) {
                        it.appendLine("}")
                    }
                }
                val jarName = "${tempFile.nameWithoutExtension}.jar"
                val command = "kotlinc ${tempFile.absolutePath} -include-runtime -d $jarName"
                val commandResponse = commander.execute(command)
                println(commandResponse)
                val executeResult = commander.execute("java -jar $jarName")
                tempFile.deleteRecursively()
                Success(executeResult.output)
            } catch (ex: Exception) {
                Error(ex.message ?: "")
            }
        }
    }
}
