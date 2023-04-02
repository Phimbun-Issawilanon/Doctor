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

class CSharpExecutor(private val commander: Commander, private val fileUtil: FileUtil) : Executor() {
    override val handledLanguages: Set<String> = setOf("text/x-csharp", "csharp", "c#")

    override suspend fun execute(code: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val tempFile = fileUtil.writeToFile("c-sharp-exc", ".cs") {
                    it.appendLine("using System;")
                    it.appendLine()
                    it.appendLine("public class Temp")
                    it.appendLine("{")
                    it.appendLine("    public static void Main(string[] args)")
                    it.appendLine("    {")
                    it.appendLine(code)
                    it.appendLine("    }")
                    it.appendLine("}")
                }
                val compileResult = commander.execute("csc ${tempFile.absolutePath}")
                tempFile.deleteRecursively()
                if (compileResult is CommandResponse.Error) {
                    Success(compileResult.output, compileResult is CommandResponse.Error)
                } else {
                    val path = System.getProperty("user.dir").plus("/${tempFile.name.replace(".cs", ".exe")}")
                    val result = commander.execute("mono $path")
                    File(path).deleteRecursively()
                    Success(result.output, result is CommandResponse.Error)
                }

            } catch (ex: Exception) {
                Error(ex.message ?: "")
            }
        }
    }

    override suspend fun test(code: String, unitTests: String): ExecutorResponse {
        TODO("Not yet implemented")
    }
}
