package ui.viewmodels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.FileItem
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
        val homeDirectory = getHomeDirectory()
        _uiState.update {
            FileExplorerState.Ready(
                currentPath = FileSystem.SYSTEM.canonicalize(homeDirectory.toPath()).toString(),
                items = listOf()
            )
        }
        screenModelScope.launch(Dispatchers.Default) {
            updateExplorerItems()
        }
    }

    // Modifiers

    suspend fun enterSubdirectory(directoryName: String) {
        if (_uiState.value !is FileExplorerState.Ready) return
        val currentPathStr = (_uiState.value as FileExplorerState.Ready).currentPath
        val currentPath = currentPathStr.toPath()
        val newPath = currentPath.resolve(directoryName)
        val newPathStr = FileSystem.SYSTEM.canonicalize(newPath).toString()
        ////////////////////////////////////////////
        println("New path : $newPathStr")
        ////////////////////////////////////////////
        try {
            updateExplorerItems()
            _uiState.update { (it as FileExplorerState.Ready).copy(currentPath = newPathStr) }
        } catch (ex: IOException) {
            println("Could not change directory to $directoryName")
        }
    }

    private suspend fun updateExplorerItems() {
        if (_uiState.value !is FileExplorerState.Ready) return
        coroutineScope {
            withContext(Dispatchers.Default) {
                val currentPath = (_uiState.value as FileExplorerState.Ready).currentPath
                /////////////////////////////
                println("current path is $currentPath")
                /////////////////////////////
                val rawElementsList = FileSystem.SYSTEM.list(currentPath.toPath())
                val newElementsList = rawElementsList.map {
                    FileItem(it.toFile().isDirectory, it.name)
                }.sort()
                // Adds the go back item if possible.
                val result = if (currentPath.toPath().toFile().parentFile == null) newElementsList else listOf(
                    FileItem(
                        true,
                        ".."
                    )
                ) + newElementsList

                _uiState.update { (it as FileExplorerState.Ready).copy(items = result) }
            }
        }
    }

    private fun getHomeDirectory(): String {
        return System.getProperty("user.home")
    }
}