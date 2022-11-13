package org.empowrco.doctor.utils.files

import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.relativeToOrSelf

interface FileUtil {
    fun writeToFile(prefix: String, suffix: String, currentDir: Boolean = false, writer: (FileWriter) -> Unit): File
    fun createTempDirectory(): File
    fun deleteFiles(file: File)
}

object RealFileUtil : FileUtil {

    override fun createTempDirectory(): File {
        val tempPath = Path(System.getProperty("user.dir"))
        return Files.createTempDirectory(tempPath, "Test").relativeToOrSelf(tempPath).toFile()
    }

    override fun deleteFiles(file: File) {
        Files.walk(file.toPath())
            .sorted(Comparator.reverseOrder())
            .map(java.nio.file.Path::toFile)
            .forEach(File::delete)
    }

    private fun createTempFile(prefix: String, suffix: String, currentDir: Boolean): File {
        val projectFile = if (currentDir) {
            val projectDir = System.getProperty("user.dir")
            File(projectDir)
        } else {
            null
        }

        val tempFile = File.createTempFile(prefix, suffix, projectFile)
        return projectFile?.let { tempFile.relativeToOrSelf(it) } ?: tempFile
    }

    override fun writeToFile(prefix: String, suffix: String, currentDir: Boolean, writer: (FileWriter) -> Unit): File {
        val tempFile = createTempFile(prefix, suffix, currentDir)
        val fileWriter = FileWriter(tempFile)
        fileWriter.use {
            writer(it)
        }
        return tempFile
    }
}
