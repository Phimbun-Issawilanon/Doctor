package org.empowrco.doctor.playground.generator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.rauschig.jarchivelib.ArchiveFormat
import org.rauschig.jarchivelib.ArchiverFactory
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString


internal interface Playground {
    suspend fun generate(code: String): Path
}

class RealPlayground: Playground {
    companion object {
        private const val archiveName = "MyPlayground"
    }
    override suspend fun generate(code: String): Path = withContext(Dispatchers.IO) {
        val tempFolder = Files.createTempDirectory("playground")
        val folderAbsolutePath = "${tempFolder.absolutePathString()}/${archiveName}.playground"
        val folderPath = Path(folderAbsolutePath)
        val folder = Files.createDirectory(folderPath)
        createFile(
            folder = folder,
            filename = "Contents.swift",
            defaultText = "import Foundation\n$code"
        )

        createFile(
            folder = folder,
            filename = "contents.xcplayground",
            defaultText = generateDefaultXml(),
        )

        generateWorkspace(folder)
        val compressedPath = compress(tempFolder)
        return@withContext compressedPath
    }

    private fun generateWorkspace(folder: Path) {
        val contentsAbsolutePath = "${folder.absolutePathString()}/playground.xcworkspace"
        val contentsPath = Path(contentsAbsolutePath)
        val contentsFile = Files.createDirectory(contentsPath)

        createFile(contentsFile, "contents.xcworkspacedata", generateWorkspaceXml())
    }

    private fun generateWorkspaceXml(): String {
        val builder = StringBuilder()
        builder.appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        builder.appendLine("<Workspace")
        builder.appendLine("   version = \"1.0\">")
        builder.appendLine("   <FileRef")
        builder.appendLine("      location = \"self:\">")
        builder.appendLine("   </FileRef>")
        builder.appendLine("</Workspace>")
        return builder.toString()
    }

    private fun generateDefaultXml(): String {
        val builder = StringBuilder()
        builder.appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
        builder.appendLine("<playground version='5.0' target-platform='ios' buildActiveScheme='true' importAppTypes='true'>")
        builder.appendLine("    <timeline fileName='timeline.xctimeline'/>")
        builder.appendLine("</playground>")
        return builder.toString()
    }

    private fun createFile(folder: Path, filename: String, defaultText: String) {
        val contentsAbsolutePath = "${folder.absolutePathString()}/${filename}"
        val contentsPath = Path(contentsAbsolutePath)
        val contentsFile = Files.createFile(contentsPath).toFile()
        contentsFile.writer().use {
            it.write(defaultText)
        }
    }

    private suspend fun compress(tempFolder: Path): Path = withContext(Dispatchers.IO) {
        val destination = tempFolder.toFile()
        val source = tempFolder.toFile()

        val archiver = ArchiverFactory.createArchiver(ArchiveFormat.ZIP)
        val archive = archiver.create(archiveName, destination, source)
        return@withContext archive.toPath()
    }
}