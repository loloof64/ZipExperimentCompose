package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composables.icons.FileIcon
import composables.icons.FolderIcon
import models.FileItem
import models.sort
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import zipexperiment.composeapp.generated.resources.Res
import zipexperiment.composeapp.generated.resources.file_item_icon

@Composable
fun Loading(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(modifier = modifier.fillMaxSize(0.8f), color = Color.Blue)
}

@Composable
fun FileExplorer(
    modifier: Modifier = Modifier,
    items: List<FileItem>,
    currentPath: String,
    verticalGap: Dp = 10.dp,
    horizontalGap: Dp = 10.dp,
    iconSize: Dp = 50.dp,
    contentFontSize: TextUnit = 16.sp,
    pathBarFontSize: TextUnit = 20.sp,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ExplorerBar(path = currentPath, fontSize = pathBarFontSize)
        ExplorerContent(
            items = items,
            verticalGap = verticalGap,
            horizontalGap = horizontalGap,
            iconSize = iconSize,
            fontSize = contentFontSize
        )
    }
}

@Composable
fun ExplorerContent(
    modifier: Modifier = Modifier,
    items: List<FileItem>,
    verticalGap: Dp = 10.dp,
    horizontalGap: Dp = 10.dp,
    iconSize: Dp = 50.dp,
    fontSize: TextUnit = 16.sp,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(verticalGap)
    ) {
        items(items) { currentItem ->
            FileItemRow(item = currentItem, iconSize = iconSize, fontSize = fontSize, spaceBetween = horizontalGap)
        }
    }
}

@Composable
fun ExplorerBar(
    modifier: Modifier = Modifier,
    path: String,
    fontSize: TextUnit = 20.sp,
) {
    Text(
        modifier = modifier.fillMaxWidth().background(color = Color.Green.copy(alpha = 0.4f)).horizontalScroll(
            rememberScrollState()
        ),
        text = path,
        fontSize = fontSize,
        overflow = TextOverflow.Visible,
        softWrap = false,
        maxLines = 1,
    )
}

@Composable
fun FileItemRow(
    modifier: Modifier = Modifier,
    item: FileItem,
    iconSize: Dp = 50.dp,
    fontSize: TextUnit = 16.sp,
    spaceBetween: Dp = 10.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FileItemIcon(isFolder = item.isFolder, modifier = Modifier.size(iconSize))
        FileItemName(fileName = item.name, fontSize = fontSize)
    }
}

@Composable
fun FileItemIcon(isFolder: Boolean, modifier: Modifier = Modifier) {
    Image(
        imageVector = if (isFolder) FolderIcon else FileIcon,
        contentDescription = stringResource(Res.string.file_item_icon),
        modifier = modifier
    )
}

@Composable
fun FileItemName(modifier: Modifier = Modifier, fileName: String, fontSize: TextUnit) {
    Text(
        modifier = modifier,
        text = fileName,
        fontSize = fontSize,
        fontWeight = FontWeight.Black,
        overflow = TextOverflow.Visible
    )
}

///////////////////////////////
// Previews
///////////////////////////////

val items = listOf(
    FileItem(name = "01 file", isFolder = false),
    FileItem(name = "02 folder", isFolder = true),
    FileItem(name = "03 file", isFolder = false),
    FileItem(name = "04 file", isFolder = false),
    FileItem(name = "01 folder", isFolder = true),
    FileItem(name = "02 file", isFolder = false),
    FileItem(name = "..", isFolder = true),
    FileItem(name = "03 folder", isFolder = true),
    FileItem(name = "05 folder", isFolder = true),
    FileItem(name = "04 folder", isFolder = true),
    FileItem(name = "06 file", isFolder = false),
    FileItem(name = "08 folder", isFolder = true),
    FileItem(name = "07 folder", isFolder = true),
    FileItem(name = "05 file", isFolder = false),
    FileItem(name = "10 file", isFolder = false),
    FileItem(name = "08 file", isFolder = false),
    FileItem(name = "07 file", isFolder = false),
    FileItem(name = "06 folder", isFolder = true),
    FileItem(name = "09 file", isFolder = false),
).sort()

@Composable
private fun PreviewContainer(content: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        content()
    }
}

@Preview
@Composable
fun FolderItemIconPreview() {
    PreviewContainer {
        FileItemIcon(isFolder = true, modifier = Modifier.size(100.dp))
    }
}

@Preview
@Composable
fun FileItemIconPreview() {
    PreviewContainer {
        FileItemIcon(isFolder = false, modifier = Modifier.size(100.dp))
    }
}

@Preview
@Composable
fun FileItemNamePreview() {
    PreviewContainer {
        FileItemName(fileName = "My very long file name that can't stand on a single line !", fontSize = 16.sp)
    }
}

@Preview
@Composable
fun FileItemRowForFolderPreview() {
    PreviewContainer {
        FileItemRow(item = FileItem(name = "My texts folder", isFolder = true))
    }
}

@Preview
@Composable
fun FileItemRowForFilePreview() {
    PreviewContainer {
        FileItemRow(item = FileItem(name = "My text file", isFolder = false))
    }
}

@Preview
@Composable
fun ExplorerContentPreview() {
    PreviewContainer {
        ExplorerContent(
            items = items,
        )
    }
}

@Preview
@Composable
fun ExplorerBarPreview() {
    PreviewContainer {
        ExplorerBar(path = "/home/user/Documents/MyBooks/Pdfs")
    }
}

@Preview
@Composable
fun FileExplorerPreview() {
    FileExplorer(
        items = items,
        currentPath = "/home/user/Documents/MyBooks/Pdfs"
    )
}