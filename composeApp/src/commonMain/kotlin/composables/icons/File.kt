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
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

val FileIcon: ImageVector
    get() {
        val current = _fileIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = ".FileXml",
            defaultWidth = 800.0.dp,
            defaultHeight = 800.0.dp,
            viewportWidth = 64.0f,
            viewportHeight = 64.0f,
        ).apply {
            group {
                // M46 3.41 46 14 56.59 14z
                path(
                    fill = SolidColor(Color(0xFF000000)),
                ) {
                    // M 46 3.41
                    moveTo(x = 46.0f, y = 3.41f)
                    // L 46 14
                    lineTo(x = 46.0f, y = 14.0f)
                    // L 56.59 14z
                    lineTo(x = 56.59f, y = 14.0f)
                    close()
                }
                // M45 16 a1 1 0 0 1 -1 -1 V2 H8 a2 2 0 0 0 -2 2 v56 a2 2 0 0 0 2 2 h48 a2 2 0 0 0 2 -2 V16z
                path {
                    // M 45 16
                    moveTo(x = 45.0f, y = 16.0f)
                    // a 1 1 0 0 1 -1 -1
                    arcToRelative(
                        a = 1.0f,
                        b = 1.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        dx1 = -1.0f,
                        dy1 = -1.0f,
                    )
                    // V 2
                    verticalLineTo(y = 2.0f)
                    // H 8
                    horizontalLineTo(x = 8.0f)
                    // a 2 2 0 0 0 -2 2
                    arcToRelative(
                        a = 2.0f,
                        b = 2.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = -2.0f,
                        dy1 = 2.0f,
                    )
                    // v 56
                    verticalLineToRelative(dy = 56.0f)
                    // a 2 2 0 0 0 2 2
                    arcToRelative(
                        a = 2.0f,
                        b = 2.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = 2.0f,
                        dy1 = 2.0f,
                    )
                    // h 48
                    horizontalLineToRelative(dx = 48.0f)
                    // a 2 2 0 0 0 2 -2
                    arcToRelative(
                        a = 2.0f,
                        b = 2.0f,
                        theta = 0.0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        dx1 = 2.0f,
                        dy1 = -2.0f,
                    )
                    // V 16z
                    verticalLineTo(y = 16.0f)
                    close()
                }
            }
            // M14 26 a1 1 0 0 0 1 1 h34 a1 1 0 1 0 0 -2 H15 a1 1 0 0 0 -1 1 m35 11 H15 a1 1 0 1 0 0 2 h34 a1 1 0 1 0 0 -2 m0 6 H15 a1 1 0 1 0 0 2 h34 a1 1 0 1 0 0 -2 m0 6 H15 a1 1 0 1 0 0 2 h34 a1 1 0 1 0 0 -2 m0 -18 H15 a1 1 0 1 0 0 2 h34 a1 1 0 1 0 0 -2 M15 20 h16 a1 1 0 1 0 0 -2 H15 a1 1 0 1 0 0 2
            path(
                fill = SolidColor(Color(0xFF394240)),
            ) {
                // M 14 26
                moveTo(x = 14.0f, y = 26.0f)
                // a 1 1 0 0 0 1 1
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 1.0f,
                    dy1 = 1.0f,
                )
                // h 34
                horizontalLineToRelative(dx = 34.0f)
                // a 1 1 0 1 0 0 -2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -2.0f,
                )
                // H 15
                horizontalLineTo(x = 15.0f)
                // a 1 1 0 0 0 -1 1
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -1.0f,
                    dy1 = 1.0f,
                )
                // m 35 11
                moveToRelative(dx = 35.0f, dy = 11.0f)
                // H 15
                horizontalLineTo(x = 15.0f)
                // a 1 1 0 1 0 0 2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 2.0f,
                )
                // h 34
                horizontalLineToRelative(dx = 34.0f)
                // a 1 1 0 1 0 0 -2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -2.0f,
                )
                // m 0 6
                moveToRelative(dx = 0.0f, dy = 6.0f)
                // H 15
                horizontalLineTo(x = 15.0f)
                // a 1 1 0 1 0 0 2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 2.0f,
                )
                // h 34
                horizontalLineToRelative(dx = 34.0f)
                // a 1 1 0 1 0 0 -2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -2.0f,
                )
                // m 0 6
                moveToRelative(dx = 0.0f, dy = 6.0f)
                // H 15
                horizontalLineTo(x = 15.0f)
                // a 1 1 0 1 0 0 2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 2.0f,
                )
                // h 34
                horizontalLineToRelative(dx = 34.0f)
                // a 1 1 0 1 0 0 -2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -2.0f,
                )
                // m 0 -18
                moveToRelative(dx = 0.0f, dy = -18.0f)
                // H 15
                horizontalLineTo(x = 15.0f)
                // a 1 1 0 1 0 0 2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 2.0f,
                )
                // h 34
                horizontalLineToRelative(dx = 34.0f)
                // a 1 1 0 1 0 0 -2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -2.0f,
                )
                // M 15 20
                moveTo(x = 15.0f, y = 20.0f)
                // h 16
                horizontalLineToRelative(dx = 16.0f)
                // a 1 1 0 1 0 0 -2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = -2.0f,
                )
                // H 15
                horizontalLineTo(x = 15.0f)
                // a 1 1 0 1 0 0 2
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 2.0f,
                )
            }
            // m59.7 14.3 -14 -14 A1 1 0 0 0 45 0 H8 a4 4 0 0 0 -4 4 v56 a4 4 0 0 0 4 4 h48 a4 4 0 0 0 4 -4 V15 a1 1 0 0 0 -.3 -.7 M46 3.4 56.59 14 H46z M58 60 a2 2 0 0 1 -2 2 H8 a2 2 0 0 1 -2 -2 V4 a2 2 0 0 1 2 -2 h36 v13 a1 1 0 0 0 1 1 h13z
            path(
                fill = SolidColor(Color(0xFF394240)),
            ) {
                // m 59.7 14.3
                moveToRelative(dx = 59.7f, dy = 14.3f)
                // l -14 -14
                lineToRelative(dx = -14.0f, dy = -14.0f)
                // A 1 1 0 0 0 45 0
                arcTo(
                    horizontalEllipseRadius = 1.0f,
                    verticalEllipseRadius = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 45.0f,
                    y1 = 0.0f,
                )
                // H 8
                horizontalLineTo(x = 8.0f)
                // a 4 4 0 0 0 -4 4
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -4.0f,
                    dy1 = 4.0f,
                )
                // v 56
                verticalLineToRelative(dy = 56.0f)
                // a 4 4 0 0 0 4 4
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 4.0f,
                    dy1 = 4.0f,
                )
                // h 48
                horizontalLineToRelative(dx = 48.0f)
                // a 4 4 0 0 0 4 -4
                arcToRelative(
                    a = 4.0f,
                    b = 4.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 4.0f,
                    dy1 = -4.0f,
                )
                // V 15
                verticalLineTo(y = 15.0f)
                // a 1 1 0 0 0 -0.3 -0.7
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = -0.3f,
                    dy1 = -0.7f,
                )
                // M 46 3.4
                moveTo(x = 46.0f, y = 3.4f)
                // L 56.59 14
                lineTo(x = 56.59f, y = 14.0f)
                // H 46z
                horizontalLineTo(x = 46.0f)
                close()
                // M 58 60
                moveTo(x = 58.0f, y = 60.0f)
                // a 2 2 0 0 1 -2 2
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.0f,
                    dy1 = 2.0f,
                )
                // H 8
                horizontalLineTo(x = 8.0f)
                // a 2 2 0 0 1 -2 -2
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -2.0f,
                    dy1 = -2.0f,
                )
                // V 4
                verticalLineTo(y = 4.0f)
                // a 2 2 0 0 1 2 -2
                arcToRelative(
                    a = 2.0f,
                    b = 2.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 2.0f,
                    dy1 = -2.0f,
                )
                // h 36
                horizontalLineToRelative(dx = 36.0f)
                // v 13
                verticalLineToRelative(dy = 13.0f)
                // a 1 1 0 0 0 1 1
                arcToRelative(
                    a = 1.0f,
                    b = 1.0f,
                    theta = 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 1.0f,
                    dy1 = 1.0f,
                )
                // h 13z
                horizontalLineToRelative(dx = 13.0f)
                close()
            }
            // M46 3.41 56.59 14 46 14z
            path(
                fill = SolidColor(Color(0xFF231F20)),
            ) {
                // M 46 3.41
                moveTo(x = 46.0f, y = 3.41f)
                // L 56.59 14
                lineTo(x = 56.59f, y = 14.0f)
                // L 46 14z
                lineTo(x = 46.0f, y = 14.0f)
                close()
            }
        }.build().also { _fileIcon = it }
    }

@Preview
@Composable
private fun IconPreview() {
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = FileIcon,
            contentDescription = null,
            modifier = Modifier
                .width((800.0).dp)
                .height((800.0).dp),
        )
    }
    
}

@Suppress("ObjectPropertyName")
private var _fileIcon: ImageVector? = null
