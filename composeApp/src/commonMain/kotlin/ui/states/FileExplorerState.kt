package ui.states

import models.FileItem

sealed class FileExplorerState {
    object Loading: FileExplorerState()
    data class Ready(
        val currentPath: String = "",
        val items: List<FileItem> = listOf(),
    ): FileExplorerState()
}
