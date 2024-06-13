package ui.composables.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun VectorPreview() {
    Image(ExtractIcon, null)
}

private var _Extract: ImageVector? = null

val ExtractIcon: ImageVector
		get() {
			if (_Extract != null) {
				return _Extract!!
			}
_Extract = ImageVector.Builder(
                name = "Extract",
                defaultWidth = 800.dp,
                defaultHeight = 800.dp,
                viewportWidth = 492f,
                viewportHeight = 492f
            ).apply {
				group {
					group {
						path(
    						fill = SolidColor(Color(0xFF000000)),
    						fillAlpha = 1.0f,
    						stroke = null,
    						strokeAlpha = 1.0f,
    						strokeLineWidth = 1.0f,
    						strokeLineCap = StrokeCap.Butt,
    						strokeLineJoin = StrokeJoin.Miter,
    						strokeLineMiter = 1.0f,
    						pathFillType = PathFillType.NonZero
						) {
							moveTo(484.09f, 227.108f)
							lineToRelative(-94.356f, -94.36f)
							curveToRelative(-5.064f, -5.06f, -11.82f, -7.844f, -19.028f, -7.844f)
							reflectiveCurveToRelative(-13.96f, 2.784f, -19.024f, 7.844f)
							lineToRelative(-16.12f, 16.128f)
							curveToRelative(-5.064f, 5.064f, -7.852f, 11.82f, -7.852f, 19.024f)
							curveToRelative(0f, 7.208f, 2.788f, 14.084f, 7.852f, 19.152f)
							lineToRelative(20.388f, 20.508f)
							horizontalLineTo(165.382f)
							curveToRelative(-14.836f, 0f, -26.944f, 11.824f, -26.944f, 26.656f)
							verticalLineToRelative(22.8f)
							curveToRelative(0f, 14.832f, 12.108f, 27.416f, 26.944f, 27.416f)
							horizontalLineToRelative(191.74f)
							lineToRelative(-21.52f, 21.26f)
							curveToRelative(-5.068f, 5.068f, -7.856f, 11.692f, -7.856f, 18.9f)
							curveToRelative(0f, 7.204f, 2.788f, 13.896f, 7.856f, 18.96f)
							lineToRelative(16.12f, 16.084f)
							curveToRelative(5.064f, 5.068f, 11.82f, 7.84f, 19.024f, 7.84f)
							curveToRelative(7.208f, 0.008f, 13.964f, -2.796f, 19.028f, -7.86f)
							lineToRelative(94.316f, -94.324f)
							curveToRelative(5.08f, -5.08f, 7.868f, -11.864f, 7.848f, -19.08f)
							curveTo(491.958f, 238.972f, 489.17f, 232.188f, 484.09f, 227.108f)
							close()
}
}
}
				group {
					group {
						path(
    						fill = SolidColor(Color(0xFF000000)),
    						fillAlpha = 1.0f,
    						stroke = null,
    						strokeAlpha = 1.0f,
    						strokeLineWidth = 1.0f,
    						strokeLineCap = StrokeCap.Butt,
    						strokeLineJoin = StrokeJoin.Miter,
    						strokeLineMiter = 1.0f,
    						pathFillType = PathFillType.NonZero
						) {
							moveTo(265.59f, 76.876f)
							curveToRelative(1.844f, 11.532f, 13.084f, 23.064f, 26.644f, 23.064f)
							horizontalLineToRelative(22.796f)
							curveToRelative(14.724f, 0f, 27.12f, -12.308f, 27.12f, -27.036f)
							verticalLineToRelative(-34.4f)
							curveTo(342.15f, 17.308f, 324.95f, 0f, 303.758f, 0f)
							horizontalLineTo(38.662f)
							curveToRelative(-21.2f, 0f, -38.6f, 17.308f, -38.6f, 38.504f)
							verticalLineToRelative(414.904f)
							curveTo(0.062f, 474.6f, 17.466f, 492f, 38.662f, 492f)
							horizontalLineToRelative(265.096f)
							curveToRelative(21.192f, 0f, 38.392f, -17.404f, 38.392f, -38.592f)
							verticalLineToRelative(-34.592f)
							curveToRelative(0f, -14.728f, -12.4f, -26.752f, -27.12f, -26.752f)
							horizontalLineToRelative(-22.796f)
							curveToRelative(-13.616f, 0f, -24.916f, 11.532f, -26.672f, 23.064f)
							horizontalLineTo(76.938f)
							verticalLineTo(76.876f)
							horizontalLineTo(265.59f)
							close()
}
}
}
}.build()
return _Extract!!
		}

