package org.empowrco.doctor.utils

import java.io.File
import java.io.FileWriter

interface FileUtil {
    fun writeToFile(prefix: String, suffix: String, writer: (FileWriter) -> Unit): File
}

object RealFileUtil : FileUtil {
    private fun createTempFile(prefix: String, suffix: String): File {
        val projectDir = System.getProperty("user.dir")
        val projectFile = File(projectDir)
        val tempFile = File.createTempFile(prefix, suffix, projectFile)
        return tempFile.relativeToOrSelf(projectFile)
    }

    override fun writeToFile(prefix: String, suffix: String, writer: (FileWriter) -> Unit): File {
        val tempFile = createTempFile(prefix, suffix)
        val fileWriter = FileWriter(tempFile)
        fileWriter.use {
            writer(it)
        }
        return tempFile
    }
}
