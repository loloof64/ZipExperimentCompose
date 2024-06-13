package ui.viewmodels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import getStartDirectory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.FileItem
import models.filterByExtensions
import models.sort
import okio.FileSystem
import okio.IOException
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import ui.states.FileExplorerState
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import kotlin.io.path.createDirectories

fun String.replaceExtension(newExtension: String): String {
    val isAFolder = !this.contains(".")
    return if (isAFolder) "$this.$newExtension" else (this.split(".").dropLast(1) + newExtension).joinToString(".")
}

class FileExplorerScreenModel : ScreenModel {
    // State
    private val _uiState: MutableStateFlow<FileExplorerState> = MutableStateFlow(FileExplorerState.Loading)
    var uiState = _uiState.asStateFlow()

    init {
        val startDirectory = getStartDirectory()
        _uiState.update {
            FileExplorerState.Ready(
                currentPath = FileSystem.SYSTEM.canonicalize(startDirectory.toPath()).toString(),
                items = listOf()
            )
        }
        screenModelScope.launch(Dispatchers.Default) {
            updateExplorerItems((_uiState.value as FileExplorerState.Ready).currentPath)
        }
    }

    fun itemExists(itemName: String): Boolean {
        val currentPathStr = (_uiState.value as FileExplorerState.Ready).currentPath
        val currentPath = currentPathStr.toPath()
        val newPath = currentPath.resolve(itemName)
        return FileSystem.SYSTEM.exists(newPath)
    }

    // Modifiers

    fun enterSubdirectory(directoryName: String) {
        if (_uiState.value !is FileExplorerState.Ready) return

        val currentPathStr = (_uiState.value as FileExplorerState.Ready).currentPath
        val currentPath = currentPathStr.toPath()
        val newPath = currentPath.resolve(directoryName)
        val newPathStr = FileSystem.SYSTEM.canonicalize(newPath).toString()

        screenModelScope.launch {
            try {
                updateExplorerItems(newPathStr)
                _uiState.update { (it as FileExplorerState.Ready).copy(currentPath = newPathStr) }
            } catch (ex: IOException) {
                println("Could not change directory to $directoryName")
                println(ex.localizedMessage)
            }
        }
    }

    /**
     * Caution : this may overwrite exisiting folder !
     */
    fun addFolder(directoryName: String, onError: () -> Unit) {
        if (_uiState.value !is FileExplorerState.Ready) return

        val currentPathStr = (_uiState.value as FileExplorerState.Ready).currentPath
        val currentPath = currentPathStr.toPath()
        val newPath = currentPath.resolve(directoryName)

        fun effectiveCreationCode() {
            FileSystem.SYSTEM.createDirectory(dir = newPath, mustCreate = true)
            updateExplorerItemsForCurrentPath()
        }

        try {
            effectiveCreationCode()
        } catch (ex: IOException) {
            try {
                FileSystem.SYSTEM.deleteRecursively(newPath)
                effectiveCreationCode()
            } catch (ex: IOException) {
                println("Could not create directory $newPath !")
                onError()
            }
        }
    }

    /**
     * Caution : this may overwrite exisiting file !
     */
    fun addTextFile(fileName: String, content: String, onError: () -> Unit) {
        if (_uiState.value !is FileExplorerState.Ready) return

        val currentPathStr = (_uiState.value as FileExplorerState.Ready).currentPath
        val currentPath = currentPathStr.toPath()
        val newPath = currentPath.resolve(fileName)

        try {
            FileSystem.SYSTEM.write(newPath) {
                writeUtf8(content)
            }
            updateExplorerItemsForCurrentPath()
        } catch (ex: IOException) {
            println("Could not create file $newPath !")
            onError()
        }
    }

    fun deleteItem(itemName: String, onSuccess: () -> Unit, onError: () -> Unit) {
        if (_uiState.value !is FileExplorerState.Ready) return

        val currentPathStr = (_uiState.value as FileExplorerState.Ready).currentPath
        val currentPath = currentPathStr.toPath()
        val newPath = currentPath.resolve(itemName)

        if (!FileSystem.SYSTEM.exists(newPath)) return

        try {
            FileSystem.SYSTEM.deleteRecursively(newPath)
            updateExplorerItemsForCurrentPath()
            onSuccess()
        } catch (ex: IOException) {
            println("Failed to delete $itemName !")
            onError()
        }
    }

    fun compressItem(itemName: String, onSuccess: () -> Unit, onError: () -> Unit) {
        if (_uiState.value !is FileExplorerState.Ready) return

        val newItemName = itemName.replaceExtension("zip")
        val currentPathStr = (_uiState.value as FileExplorerState.Ready).currentPath
        val currentPath = currentPathStr.toPath()
        val targetPath = currentPath.resolve(newItemName)
        val sourcePath = currentPath.resolve(itemName)

        try {
            compressElement(sourcePath = sourcePath, targetPath = targetPath)
            updateExplorerItemsForCurrentPath()
            onSuccess()
        } catch (ex: IOException) {
            println("Failed to compress $itemName !")
            println(ex)
            onError()
        }
    }

    fun extractItem(itemName: String, onSuccess: () -> Unit, onError: () -> Unit) {
        if (_uiState.value !is FileExplorerState.Ready) return

        if (!itemName.endsWith(".zip")) {
            println("Not an archive file : $itemName !")
            onError()
            return
        }

        val currentPathStr = (_uiState.value as FileExplorerState.Ready).currentPath
        val targetRootFolder = currentPathStr.toPath()
        val zipToExtract = targetRootFolder.resolve(itemName)

        try {
            extractElement(targetFolder = targetRootFolder, zipPath = zipToExtract)
            updateExplorerItemsForCurrentPath()
            onSuccess()
        } catch (ex: IOException) {
            println("Failed to extract $itemName !")
            println(ex)
            onError()
        }
    }

    fun updateExplorerItemsForCurrentPath() {
        val currentPathStr = (_uiState.value as FileExplorerState.Ready).currentPath
        val currentPath = currentPathStr.toPath()
        screenModelScope.launch {
            val newPathStr = FileSystem.SYSTEM.canonicalize(currentPath).toString()
            updateExplorerItems(newPathStr)
        }
    }

    private suspend fun updateExplorerItems(newPath: String) {
        if (_uiState.value !is FileExplorerState.Ready) return
        coroutineScope {
            withContext(Dispatchers.Default) {
                val rawElementsList = FileSystem.SYSTEM.list(newPath.toPath())
                val newElementsList = rawElementsList.map {
                    FileItem(it.toFile().isDirectory, it.name)
                }.filterByExtensions(listOf("txt", "zip")).sort()
                // Adds the go back item if possible.
                val result = if (newPath.toPath().toFile().parentFile == null) newElementsList else listOf(
                    FileItem(
                        true,
                        ".."
                    )
                ) + newElementsList

                withContext(Dispatchers.Main) {
                    _uiState.update { (it as FileExplorerState.Ready).copy(items = result) }
                }
            }
        }
    }

    private fun compressElement(sourcePath: Path, targetPath: Path) {
        ZipOutputStream(FileSystem.SYSTEM.sink(targetPath).buffer().outputStream()).use { zipOut ->
            sourcePath.toFile().walkTopDown().forEach { currentFile ->
                val zipFileName =
                    currentFile.absolutePath.removePrefix(sourcePath.toFile().absolutePath).removePrefix("/")
                val entry = ZipEntry("$zipFileName${if (currentFile.isDirectory) "/" else ""}")
                zipOut.putNextEntry(entry)
                if (currentFile.isFile) {
                    currentFile.inputStream().use { fis -> fis.copyTo(zipOut) }
                }
            }
        }
    }

    private fun extractElement(zipPath: Path, targetFolder: Path) {
        ZipInputStream(FileSystem.SYSTEM.source(zipPath).buffer().inputStream()).use { zipIn ->
            generateSequence { zipIn.nextEntry }.forEach { zipEntry ->
                if (!zipEntry.isDirectory) {
                    val targetPath = targetFolder.resolve(zipEntry.name)
                    targetPath.parent?.toNioPath()?.createDirectories()
                    FileSystem.SYSTEM.sink(targetPath).buffer().outputStream().use { fos ->
                        fos.write(zipIn.readBytes())
                    }
                }
            }
        }
    }

}