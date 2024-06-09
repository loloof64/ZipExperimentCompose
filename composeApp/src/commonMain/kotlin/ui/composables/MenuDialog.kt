package ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import zipexperiment.composeapp.generated.resources.Res
import zipexperiment.composeapp.generated.resources.cancel
import zipexperiment.composeapp.generated.resources.validate

/*
A single action for the menu.
In onSelected, you don't have to handle a dismiss action.
 */
data class DialogAction(val caption: String, val onSelected: () -> Unit)

/**
 * You don't have to handle a dismiss action in the actions callbacks : it'll be done
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
                TextButton(onClick = {
                    singleAction.onSelected()
                    onDismiss()
                }) {
                    Text(singleAction.caption)
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
                Text(stringResource(Res.string.validate), color = Color.Green.copy(red = 0.5f, blue = 0.8f, green = 0.6f))
            }
        }
    }, text = {
        TextField(currentText, onValueChange = { currentText = it })
    }, title = { Text(titleString) })
}