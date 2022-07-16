package org.empowrco.doctor.command

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.util.Scanner
import java.util.concurrent.TimeUnit

interface Commander {
    suspend fun execute(command: String): CommandResponse
}

class RealCommander : Commander {
    companion object {
        private const val loggingCharset: String = "UTF-8"
    }
    override suspend fun execute(command: String): CommandResponse {
        var result: CommandResponse
        withContext(Dispatchers.IO) {
            result = try {
                val process = Runtime.getRuntime().exec(command)
                val output = getOutputAsync(process.inputStream)
                val error = getOutputAsync(process.errorStream)
                process.waitFor(30, TimeUnit.SECONDS)
                if (error.isBlank()) {
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

    private fun getOutputAsync(inputStream: InputStream): String {
        var result = ""
        val scanner = Scanner(inputStream, loggingCharset)
        scanner.use {
            while (scanner.hasNextLine()) {
                synchronized(this) {
                    result += scanner.nextLine()
                }
            }
        }
        return result
    }
}