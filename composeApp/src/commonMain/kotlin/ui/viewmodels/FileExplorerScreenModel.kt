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
import okio.Path.Companion.toPath
import ui.states.FileExplorerState

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

    fun itemExists(itemName: String) : Boolean {
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

        try {
            FileSystem.SYSTEM.createDirectory(newPath)
            screenModelScope.launch {
                val newPathStr = FileSystem.SYSTEM.canonicalize(currentPath).toString()
                updateExplorerItems(newPathStr)
            }
        } catch (ex: IOException) {
            println("Could not create directory $newPath !")
            onError()
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
            screenModelScope.launch {
                val newPathStr = FileSystem.SYSTEM.canonicalize(currentPath).toString()
                updateExplorerItems(newPathStr)
            }
        } catch (ex: IOException) {
            println("Could not create file $newPath !")
            onError()
        }
    }

    private suspend fun updateExplorerItems(newPath: String) {
        if (_uiState.value !is FileExplorerState.Ready) return
        coroutineScope {
            withContext(Dispatchers.Default) {
                val rawElementsList = FileSystem.SYSTEM.list(newPath.toPath())
                val newElementsList = rawElementsList.map {
                    FileItem(it.toFile().isDirectory, it.name)
                }.filterByExtensions(listOf("txt")).sort()
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
}