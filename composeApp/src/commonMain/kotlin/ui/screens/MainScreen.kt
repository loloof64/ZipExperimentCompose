package ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import composables.FileExplorer
import composables.Loading
import models.FileItem
import ui.states.FileExplorerState
import ui.viewmodels.FileExplorerScreenModel

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { FileExplorerScreenModel() }
        val state = screenModel.uiState.collectAsState()

        fun onItemSelected(item: FileItem) {
            if (!item.isFolder) return
            screenModel.enterSubdirectory(item.name)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MainScreenTopBar()
            }
        ) {
            when (state.value) {
                is FileExplorerState.Ready -> FileExplorer(
                    modifier = Modifier.padding(it).fillMaxSize(),
                    items = (state.value as FileExplorerState.Ready).items,
                    currentPath = (state.value as FileExplorerState.Ready).currentPath,
                    onItemSelected = ::onItemSelected
                    )
                else -> Loading()
            }
        }
    }
}

@Composable
fun MainScreenTopBar() {
    TopAppBar {
        Text("Zip experiment")
    }
}