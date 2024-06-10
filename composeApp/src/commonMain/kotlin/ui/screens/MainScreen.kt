package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import composables.FileExplorer
import composables.Loading
import composables.icons.FileIcon
import composables.icons.FolderIcon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import models.FileItem
import org.jetbrains.compose.resources.stringResource
import ui.composables.DialogAction
import ui.composables.MenuConfirm
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
        var dialogOverwriteFileOpen by rememberSaveable { mutableStateOf(false) }
        var dialogOverwriteFolderOpen by rememberSaveable { mutableStateOf(false) }
        var overWritingFileName by rememberSaveable { mutableStateOf<String?>(null) }
        var overWritingFolderName by rememberSaveable { mutableStateOf<String?>(null) }

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

        fun cancelOverWritingFile() {
            dialogOverwriteFileOpen = false
        }

        fun handleFilePrompt(fileName: String, warnAboutOverwrite: Boolean = true) {
            dialogOverwriteFileOpen = false
            dialogNewFileNameOpen = false
            val checkedFileName = if (fileName.endsWith(".txt")) fileName else "$fileName.txt"
            if (screenModel.itemExists(checkedFileName) && warnAboutOverwrite) {
                overWritingFileName = checkedFileName
                dialogOverwriteFileOpen = true
                return
            }
            else {
                overWritingFileName = null
            }
            val content = """
            This is a simple file text
            that spans on several lines.
            (Created at ${Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())})

            """.trimIndent()
            screenModel.addTextFile(fileName = checkedFileName, content = content, onError = {
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

        fun cancelOverWritingFolder() {
            dialogOverwriteFolderOpen = false
        }

        fun handleFolderPrompt(folderName: String, warnAboutOverwrite: Boolean = true) {
            dialogOverwriteFolderOpen = false
            dialogNewFolderNameOpen = false
            if (screenModel.itemExists(folderName) && warnAboutOverwrite) {
                overWritingFolderName = folderName
                dialogOverwriteFolderOpen = true
                return
            }
            else {
                overWritingFolderName = null
            }
            screenModel.addFolder(folderName, onError = {
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
                                    leadIcon = {
                                        Icon(
                                            modifier = Modifier.size(36.dp),
                                            imageVector = FileIcon,
                                            contentDescription = stringResource(Res.string.file)
                                        )
                                    },
                                    onSelected = ::openNewFilePrompt,
                                ),
                                DialogAction(
                                    caption = stringResource(Res.string.new_folder),
                                    leadIcon = {
                                        Icon(
                                            modifier = Modifier.size(36.dp),
                                            imageVector = FolderIcon,
                                            contentDescription = stringResource(Res.string.folder),
                                            tint = Color.Yellow.copy(alpha = 0.8f),
                                        )
                                    },
                                    onSelected = ::openNewFolderPrompt,
                                ),
                            )
                        )
                    }
                    if (dialogNewFileNameOpen) {
                        MenuTextField(
                            titleString = stringResource(Res.string.new_file_name),
                            onCancelled = ::cancelNewFilePrompt,
                            onValidated = { enteredFileName ->
                                handleFilePrompt(
                                    fileName = enteredFileName,
                                    warnAboutOverwrite = true
                                )
                            }
                        )
                    }
                    if (dialogNewFolderNameOpen) {
                        MenuTextField(
                            titleString = stringResource(Res.string.new_folder_name),
                            onCancelled = ::cancelNewFolderPrompt,
                            onValidated = { enteredFolderName ->
                                handleFolderPrompt(
                                    folderName = enteredFolderName,
                                    warnAboutOverwrite = true
                                )
                            },
                        )
                    }

                    if (dialogOverwriteFileOpen && overWritingFileName != null) {
                        val fileToOverride = overWritingFileName!!

                        MenuConfirm(
                            message = stringResource(Res.string.overwrite_file_message, fileToOverride),
                            onCancelled = ::cancelOverWritingFile,
                            onValidated = {
                                handleFilePrompt(
                                    fileName = fileToOverride,
                                    warnAboutOverwrite = false
                                )
                            }
                        )

                    }

                    if (dialogOverwriteFolderOpen && overWritingFolderName != null) {
                        val folderToOverride = overWritingFolderName!!

                        MenuConfirm(
                            message = stringResource(Res.string.overwrite_folder_message, folderToOverride),
                            onCancelled = ::cancelOverWritingFolder,
                            onValidated = {
                                handleFolderPrompt(
                                    folderName = folderToOverride,
                                    warnAboutOverwrite = false,
                                )
                            }
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