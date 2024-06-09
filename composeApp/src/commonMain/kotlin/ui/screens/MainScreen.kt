package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import composables.FileExplorer
import composables.Loading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import models.FileItem
import org.jetbrains.compose.resources.stringResource
import ui.composables.DialogAction
import ui.composables.MenuPopUpDialog
import ui.composables.MenuTextField
import ui.states.FileExplorerState
import ui.viewmodels.FileExplorerScreenModel
import zipexperiment.composeapp.generated.resources.*

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val screenModel = rememberScreenModel { FileExplorerScreenModel() }
        val state = screenModel.uiState.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }
        var dialogAddItemOpen by rememberSaveable { mutableStateOf(false) }
        var dialogNewFileNameOpen by rememberSaveable { mutableStateOf(false) }
        var dialogNewFolderNameOpen by rememberSaveable { mutableStateOf(false) }

        val failedCreatingFolderStr = stringResource(Res.string.failed_creating_folder)
        val failedCreatingFileStr = stringResource(Res.string.failed_creating_file)

        fun onItemSelected(item: FileItem) {
            if (!item.isFolder) return
            screenModel.enterSubdirectory(item.name)
        }

        fun onCreateItem() {
            dialogAddItemOpen = true
        }

        fun openNewFilePrompt() {
            dialogNewFileNameOpen = true
        }

        fun cancelNewFilePrompt() {
            dialogNewFileNameOpen = false
        }

        fun handleFilePrompt(value: String) {
            val fileName = if (value.endsWith(".txt")) value else value + ".txt"
            val content = """
            This is a simple file text
            that spans on several lines.
            (Created at ${Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())})

            """.trimIndent()
            dialogNewFileNameOpen = false
            screenModel.addTextFile(fileName = fileName, content = content, onError = {
                scope.launch(Dispatchers.Main) {
                    snackbarHostState.showSnackbar(failedCreatingFileStr)
                }
            })
        }

        fun openNewFolderPrompt() {
            dialogNewFolderNameOpen = true
        }

        fun cancelNewFolderPrompt() {
            dialogNewFolderNameOpen = false
        }

        fun handleFolderPrompt(value: String) {
            dialogNewFolderNameOpen = false
            screenModel.addFolder(value, onError = {
                scope.launch(Dispatchers.Main) {
                    snackbarHostState.showSnackbar(failedCreatingFolderStr)
                }
            })
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                MainScreenTopBar(onAddItemCallback = ::onCreateItem)
            }
        ) {
            when (state.value) {
                is FileExplorerState.Ready -> {
                    FileExplorer(
                        modifier = Modifier.padding(it).fillMaxSize(),
                        items = (state.value as FileExplorerState.Ready).items,
                        currentPath = (state.value as FileExplorerState.Ready).currentPath,
                        onItemSelected = ::onItemSelected
                    )
                    if (dialogAddItemOpen) {
                        MenuPopUpDialog(
                            onDismiss = { dialogAddItemOpen = false }, actions = listOf(
                                DialogAction(
                                    caption = stringResource(Res.string.new_file),
                                    onSelected = ::openNewFilePrompt,
                                ),
                                DialogAction(
                                    caption = stringResource(Res.string.new_folder),
                                    onSelected = ::openNewFolderPrompt,
                                ),
                            )
                        )
                    }
                    if (dialogNewFileNameOpen) {
                        MenuTextField(
                            titleString = stringResource(Res.string.new_file_name),
                            onCancelled = ::cancelNewFilePrompt,
                            onValidated = ::handleFilePrompt
                        )
                    }
                    if (dialogNewFolderNameOpen) {
                        MenuTextField(
                            titleString = stringResource(Res.string.new_folder_name),
                            onCancelled = ::cancelNewFolderPrompt,
                            onValidated = ::handleFolderPrompt,
                        )
                    }
                }

                else -> Loading()
            }
        }
    }
}

@Composable
fun MainScreenTopBar(
    onAddItemCallback: () -> Unit = {}
) {
    TopAppBar(contentPadding = PaddingValues(8.dp)) {
        Text("Zip experiment", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onAddItemCallback) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.AddCircle,
                contentDescription = stringResource(Res.string.add_item)
            )
        }
    }
}