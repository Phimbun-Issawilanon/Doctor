package org.empowrco.doctor.executor

import io.ktor.utils.io.core.use
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.empowrco.doctor.command.CommandResponse
import org.empowrco.doctor.command.Commander
import org.empowrco.doctor.models.Error
import org.empowrco.doctor.models.ExecutorResponse
import org.empowrco.doctor.models.Success
import org.empowrco.doctor.utils.files.FileUtil
import java.io.File
import java.io.FileWriter

internal class SwiftExecutor(private val commander: Commander, private val fileUtil: FileUtil) : Executor() {
    override val handledLanguages = setOf("swift", "text/x-swift")
    override suspend fun execute(code: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val tempFile = fileUtil.writeToFile("swift-exc", ".swift") {
                    it.appendLine("import Foundation")
                    it.appendLine(code)
                }
                val result = commander.execute("swift ${tempFile.absolutePath}")
                tempFile.deleteRecursively()
                Success(result.output, result is CommandResponse.Error)
            } catch (ex: Exception) {
                Error(ex.message ?: "")
            }
        }
    }

    override suspend fun test(code: String, unitTests: String): ExecutorResponse {
        return withContext(Dispatchers.IO) {
            val tempFolder = fileUtil.createTempDirectory()
            return@withContext try {
                val nameRegex = "class\\s(.*):?\\sXCTestCase".toRegex()
                val testName =
                    nameRegex.find(unitTests)?.groupValues?.lastOrNull()?.removeSuffix(":")?.removeSuffix("Tests")
                        ?: return@withContext Error("Unit tests did not include a valid class extending XCTestCase")
                val createTestResult =
                    commander.execute("swift package init --type library --name $testName", tempFolder)
                val sourcesFolder = File(tempFolder, "/Sources/$testName")
                val sourceFile = sourcesFolder.listFiles()?.firstOrNull() ?: return@withContext Error(
                    "Error finding " +
                            "source file for Test $testName"
                )
                FileWriter(sourceFile).use {
                    it.write("")
                    it.appendLine("import Foundation")
                    it.appendLine(code)
                }
                val testsFolder = File(tempFolder, "/Tests/${testName}Tests")
                val testsFile = testsFolder.listFiles()?.firstOrNull() ?: return@withContext Error(
                    "Error finding " +
                            "test file for Test $testName"
                )
                FileWriter(testsFile).use {
                    it.write(unitTests)
                }
                if (createTestResult is CommandResponse.Error) {
                    return@withContext Error(createTestResult.output)
                }
                val executeTestResult = commander.execute("swift test", tempFolder)
                if (executeTestResult is CommandResponse.Success) {
                    return@withContext Success(executeTestResult.output, false)
                } else {
                    return@withContext Error(executeTestResult.output)
                }
            } catch (ex: Exception) {
                Error(ex.message ?: "")
            } finally {
                fileUtil.deleteFiles(tempFolder)
            }
        }
    }
}
