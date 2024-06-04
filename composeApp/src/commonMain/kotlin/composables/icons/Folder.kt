package composables.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

val FolderIcon: ImageVector
    get() {
        val current = _folderIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = ".FolderXml",
            defaultWidth = 800.0.dp,
            defaultHeight = 800.0.dp,
            viewportWidth = 1024.0f,
            viewportHeight = 1024.0f,
        ).apply {
            // M853.33 256 h-384 L384 170.67 H170.67 c-46.94 0 -85.34 38.4 -85.34 85.33 v170.67 h853.34 v-85.34 c0 -46.93 -38.4 -85.33 -85.34 -85.33
            path(
                fill = SolidColor(Color(0xFFFFA000)),
            ) {
                // M 853.33 256
                moveTo(x = 853.33f, y = 256.0f)
                // h -384
                horizontalLineToRelative(dx = -384.0f)
                // L 384 170.67
                lineTo(x = 384.0f, y = 170.67f)
                // H 170.67
                horizontalLineTo(x = 170.67f)
                // c -46.94 0 -85.34 38.4 -85.34 85.33
                curveToRelative(
                    dx1 = -46.94f,
                    dy1 = 0.0f,
                    dx2 = -85.34f,
                    dy2 = 38.4f,
                    dx3 = -85.34f,
                    dy3 = 85.33f,
                )
                // v 170.67
                verticalLineToRelative(dy = 170.67f)
                // h 853.34
                horizontalLineToRelative(dx = 853.34f)
                // v -85.34
                verticalLineToRelative(dy = -85.34f)
                // c 0 -46.93 -38.4 -85.33 -85.34 -85.33
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -46.93f,
                    dx2 = -38.4f,
                    dy2 = -85.33f,
                    dx3 = -85.34f,
                    dy3 = -85.33f,
                )
            }
            // M853.33 256 H170.67 c-46.94 0 -85.34 38.4 -85.34 85.33 V768 c0 46.93 38.4 85.33 85.34 85.33 h682.66 c46.94 0 85.34 -38.4 85.34 -85.33 V341.33 c0 -46.93 -38.4 -85.33 -85.34 -85.33
            path(
                fill = SolidColor(Color(0xFFFFCA28)),
            ) {
                // M 853.33 256
                moveTo(x = 853.33f, y = 256.0f)
                // H 170.67
                horizontalLineTo(x = 170.67f)
                // c -46.94 0 -85.34 38.4 -85.34 85.33
                curveToRelative(
                    dx1 = -46.94f,
                    dy1 = 0.0f,
                    dx2 = -85.34f,
                    dy2 = 38.4f,
                    dx3 = -85.34f,
                    dy3 = 85.33f,
                )
                // V 768
                verticalLineTo(y = 768.0f)
                // c 0 46.93 38.4 85.33 85.34 85.33
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = 46.93f,
                    dx2 = 38.4f,
                    dy2 = 85.33f,
                    dx3 = 85.34f,
                    dy3 = 85.33f,
                )
                // h 682.66
                horizontalLineToRelative(dx = 682.66f)
                // c 46.94 0 85.34 -38.4 85.34 -85.33
                curveToRelative(
                    dx1 = 46.94f,
                    dy1 = 0.0f,
                    dx2 = 85.34f,
                    dy2 = -38.4f,
                    dx3 = 85.34f,
                    dy3 = -85.33f,
                )
                // V 341.33
                verticalLineTo(y = 341.33f)
                // c 0 -46.93 -38.4 -85.33 -85.34 -85.33
                curveToRelative(
                    dx1 = 0.0f,
                    dy1 = -46.93f,
                    dx2 = -38.4f,
                    dy2 = -85.33f,
                    dx3 = -85.34f,
                    dy3 = -85.33f,
                )
            }
        }.build().also { _folderIcon = it }
    }

@Preview
@Composable
private fun IconPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = FolderIcon,
            contentDescription = null,
            modifier = Modifier
                .width((800.0).dp)
                .height((800.0).dp),
        )
    }
}

@Suppress("ObjectPropertyName")
private var _folderIcon: ImageVector? = null
