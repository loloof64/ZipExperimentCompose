package ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import zipexperiment.composeapp.generated.resources.*
import zipexperiment.composeapp.generated.resources.Res
import zipexperiment.composeapp.generated.resources.cancel
import zipexperiment.composeapp.generated.resources.no
import zipexperiment.composeapp.generated.resources.validate

/*
A single action for the menu.
 */
data class DialogAction(val caption: String, val leadIcon: @Composable () -> Unit, val onSelected: () -> Unit)

/**
 * You do have to handle a dismiss action in the actions callbacks : it won't be done
 * automatically with your onDismiss parameter.
 */
@Composable
fun MenuPopUpDialog(onDismiss: () -> Unit, actions: List<DialogAction>) {
    AlertDialog(onDismissRequest = onDismiss, buttons = {}, text = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            for (singleAction in actions) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    singleAction.leadIcon()
                    TextButton(onClick = {
                        singleAction.onSelected()
                    }) {
                        Text(singleAction.caption)
                    }
                }
            }
        }
    })
}

/**
 * Notice that it's your responsability to close yourself the dialog from both callbacks.
 */
@Composable
fun MenuTextField(
    titleString: String = "",
    onCancelled: () -> Unit,
    onValidated: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var currentText by rememberSaveable { mutableStateOf("") }
    AlertDialog(onDismissRequest = onCancelled, buttons = {
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onCancelled) {
                Text(stringResource(Res.string.cancel), color = Color.Red)
            }
            Spacer(modifier = Modifier.width(20.dp))
            TextButton(onClick = { onValidated(currentText) }) {
                Text(
                    stringResource(Res.string.validate),
                    color = Color.Blue
                )
            }
        }
    }, text = {
        TextField(
            currentText, onValueChange = { currentText = it },
            modifier = Modifier.focusRequester(focusRequester)
        )
    }, title = { Text(titleString) })

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun MenuConfirm(
    message: String,
    onCancelled: () -> Unit,
    onValidated: () -> Unit,
) {
    AlertDialog(onDismissRequest = onCancelled, buttons = {
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onCancelled) {
                Text(stringResource(Res.string.no), color = Color.Red)
            }
            Spacer(modifier = Modifier.width(20.dp))
            TextButton(onClick = onValidated) {
                Text(
                    stringResource(Res.string.yes),
                    color = Color.Blue
                )
            }
        }
    }, text = { Text(message) })
}