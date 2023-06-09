package org.empowrco.doctor.command

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.util.Scanner
import java.util.concurrent.TimeUnit

interface Commander {
    suspend fun execute(command: String, workingDirectory: File? = null): CommandResponse
}

class RealCommander : Commander {
    companion object {
        private const val loggingCharset: String = "UTF-8"
    }

    override suspend fun execute(command: String, workingDirectory: File?): CommandResponse {
        var result: CommandResponse
        withContext(Dispatchers.IO) {
            result = try {
                val process = Runtime.getRuntime().exec(command, null, workingDirectory)
                val output = getOutput(process.inputStream)
                val error = getOutput(process.errorStream)
                process.waitFor(30, TimeUnit.SECONDS)
                if (output.isNotBlank()) {
                    CommandResponse.Success(output)
                } else {
                    CommandResponse.Error(error)
                }
            } catch (ex: Exception) {
                CommandResponse.Error(ex.localizedMessage)
            }

        }
        return result
    }

    private fun getOutput(inputStream: InputStream): String {
        val result = StringBuilder("")
        val scanner = Scanner(inputStream, loggingCharset)
        scanner.use {
            while (scanner.hasNextLine()) {
                synchronized(this) {
                    result.appendLine(scanner.nextLine())
                }
            }
        }
        return result.toString().trim()
    }
}
