package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
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
import composables.icons.ZipIcon
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
import ui.composables.icons.ExtractIcon
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
        var dialogLongItemPressedMenuOpen by rememberSaveable { mutableStateOf(false) }
        var dialogDeleteItemOpen by rememberSaveable { mutableStateOf(false) }
        var overWritingFileName by rememberSaveable { mutableStateOf<String?>(null) }
        var overWritingFolderName by rememberSaveable { mutableStateOf<String?>(null) }
        var longPressItemToProcess by rememberSaveable { mutableStateOf<FileItem?>(null) }

        val failedCreatingFolderStr = stringResource(Res.string.failed_creating_folder)
        val failedCreatingFileStr = stringResource(Res.string.failed_creating_file)
        val failedDeletingFolderStr = stringResource(Res.string.failed_deleting_folder)
        val failedDeletingFileStr = stringResource(Res.string.failed_deleting_file)
        val failedToCompressItemStr = stringResource(Res.string.failed_compressing_item)
        val failedToExtractItemStr = stringResource(Res.string.failed_extracting_item)

        fun onItemSelected(item: FileItem) {
            if (!item.isFolder) return
            screenModel.enterSubdirectory(item.name)
        }

        fun onItemLongPress(item: FileItem) {
            longPressItemToProcess = item
            dialogLongItemPressedMenuOpen = true
        }

        fun onCreateItem() {
            dialogAddItemOpen = true
        }

        fun openNewFilePrompt() {
            dialogAddItemOpen = false
            dialogNewFileNameOpen = true
        }

        fun cancelNewFilePrompt() {
            dialogNewFileNameOpen = false
        }

        fun cancelOverWritingFile() {
            overWritingFileName = null
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
            } else {
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
            dialogAddItemOpen = false
            dialogNewFolderNameOpen = true
        }

        fun cancelNewFolderPrompt() {
            dialogNewFolderNameOpen = false
        }

        fun cancelOverWritingFolder() {
            overWritingFolderName = null
            dialogOverwriteFolderOpen = false
        }

        fun handleFolderPrompt(folderName: String, warnAboutOverwrite: Boolean = true) {
            dialogOverwriteFolderOpen = false
            dialogNewFolderNameOpen = false
            if (screenModel.itemExists(folderName) && warnAboutOverwrite) {
                overWritingFolderName = folderName
                dialogOverwriteFolderOpen = true
                return
            } else {
                overWritingFolderName = null
            }
            screenModel.addFolder(folderName, onError = {
                scope.launch(Dispatchers.Main) {
                    snackbarHostState.showSnackbar(failedCreatingFolderStr)
                }
            })
        }

        fun handleDeleteItemRequest() {
            dialogLongItemPressedMenuOpen = false
            dialogDeleteItemOpen = true
        }

        fun cancelDeleteItem() {
            longPressItemToProcess = null
            dialogDeleteItemOpen = false
        }

        fun deleteItem() {
            dialogDeleteItemOpen = false

            screenModel.deleteItem(longPressItemToProcess!!.name, onSuccess = {
                longPressItemToProcess = null
            }, onError = {
                scope.launch(Dispatchers.Main) {
                    val message =
                        if (longPressItemToProcess?.isFolder == true
                        ) failedDeletingFolderStr else failedDeletingFileStr
                    longPressItemToProcess = null
                    snackbarHostState.showSnackbar(message)
                }
            })
        }

        fun handleCompressElementRequest() {
            dialogLongItemPressedMenuOpen = false

            screenModel.compressItem(longPressItemToProcess!!.name, onSuccess = {
                longPressItemToProcess = null
            }, onError = {
                scope.launch(Dispatchers.Main) {
                    longPressItemToProcess = null
                    snackbarHostState.showSnackbar(failedToCompressItemStr)
                }
            })
        }

        fun reload() {
            screenModel.updateExplorerItemsForCurrentPath()
        }

        fun handleExtractElementRequest() {
            dialogLongItemPressedMenuOpen = false

            screenModel.extractItem(longPressItemToProcess!!.name, onSuccess = {
                longPressItemToProcess = null
            }, onError = {
                scope.launch(Dispatchers.Main) {
                    longPressItemToProcess = null
                    snackbarHostState.showSnackbar(failedToExtractItemStr)
                }
            })
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                MainScreenTopBar(onAddItemCallback = ::onCreateItem, onRefreshContent = ::reload)
            }
        ) {
            when (state.value) {
                is FileExplorerState.Ready -> {
                    FileExplorer(
                        modifier = Modifier.padding(it).fillMaxSize(),
                        fileIcons = mapOf("zip" to ZipIcon),
                        items = (state.value as FileExplorerState.Ready).items,
                        currentPath = (state.value as FileExplorerState.Ready).currentPath,
                        onItemSelected = ::onItemSelected,
                        onItemLongPress = ::onItemLongPress,
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
                    } else if (dialogNewFileNameOpen) {
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
                    } else if (dialogNewFolderNameOpen) {
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
                    } else if (dialogOverwriteFileOpen && overWritingFileName != null) {
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

                    } else if (dialogOverwriteFolderOpen && overWritingFolderName != null) {
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
                    } else if (dialogLongItemPressedMenuOpen) {
                        val isAnArchive =
                            (longPressItemToProcess?.isFolder == false)
                            && (longPressItemToProcess?.name?.endsWith(".zip") == true)
                        MenuPopUpDialog(
                            onDismiss = {
                                longPressItemToProcess = null
                                dialogLongItemPressedMenuOpen = false
                            },
                            actions = listOf(
                                DialogAction(
                                    caption = stringResource(Res.string.delete_item),
                                    leadIcon = {
                                        Icon(
                                            modifier = Modifier.size(36.dp),
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = stringResource(Res.string.delete_icon),
                                        )
                                    },
                                    onSelected = ::handleDeleteItemRequest,
                                ),
                                if (isAnArchive) DialogAction(
                                    caption = stringResource(Res.string.extract_item),
                                    leadIcon = {
                                        Icon(
                                            modifier = Modifier.size(36.dp),
                                            imageVector = ExtractIcon,
                                            contentDescription = stringResource(Res.string.extract_icon),
                                        )
                                    },
                                    onSelected = ::handleExtractElementRequest,
                                )
                                else DialogAction(
                                    caption = stringResource(Res.string.compress_item),
                                    leadIcon = {
                                        Icon(
                                            modifier = Modifier.size(36.dp),
                                            imageVector = ZipIcon,
                                            contentDescription = stringResource(Res.string.compress_icon),
                                        )
                                    },
                                    onSelected = ::handleCompressElementRequest,
                                )
                            )
                        )
                    } else if (dialogDeleteItemOpen && longPressItemToProcess != null) {
                        val messageId =
                            if (longPressItemToProcess!!.isFolder) Res.string.delete_folder_confirmation_message
                            else Res.string.delete_file_confirmation_message
                        MenuConfirm(
                            message = stringResource(messageId, longPressItemToProcess!!.name),
                            onCancelled = ::cancelDeleteItem,
                            onValidated = ::deleteItem,
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
    onAddItemCallback: () -> Unit = {},
    onRefreshContent: () -> Unit = {},
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
        IconButton(onClick = onRefreshContent) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(Res.string.reload)
            )
        }
    }
}