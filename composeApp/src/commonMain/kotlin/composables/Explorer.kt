package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composables.icons.FileIcon
import composables.icons.FolderIcon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zipexperiment.composeapp.generated.resources.Res
import zipexperiment.composeapp.generated.resources.file_item_icon

@Composable
fun FileItemIcon(isFolder: Boolean, modifier: Modifier = Modifier) {
    Image(
        imageVector = if (isFolder) FolderIcon else FileIcon,
        contentDescription = stringResource(Res.string.file_item_icon),
        modifier = modifier
    )
}

@Preview
@Composable
fun FolderItemIconPreview() {
    FileItemIcon(isFolder = true, modifier = Modifier.size(100.dp))
}

@Preview
@Composable
fun FileItemIconPreview() {
    FileItemIcon(isFolder = false, modifier = Modifier.size(100.dp))
}