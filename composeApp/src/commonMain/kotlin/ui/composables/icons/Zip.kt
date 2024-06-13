package composables.icons

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun VectorPreview() {
    Image(ZipIcon, null)
}

private var _Zip: ImageVector? = null

val ZipIcon: ImageVector
		get() {
			if (_Zip != null) {
				return _Zip!!
			}
_Zip = ImageVector.Builder(
                name = "Zip",
                defaultWidth = 800.dp,
                defaultHeight = 800.dp,
                viewportWidth = 77.749f,
                viewportHeight = 77.749f
            ).apply {
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
						moveTo(54.04f, 0f)
						horizontalLineTo(14.908f)
						curveToRelative(-2.627f, 0f, -4.767f, 2.141f, -4.767f, 4.768f)
						verticalLineTo(72.98f)
						curveToRelative(0f, 2.631f, 2.14f, 4.769f, 4.767f, 4.769f)
						horizontalLineTo(62.84f)
						curveToRelative(2.63f, 0f, 4.768f, -2.138f, 4.768f, -4.769f)
						verticalLineTo(14.743f)
						lineTo(54.04f, 0f)
						close()
						moveTo(55.165f, 7.135f)
						lineToRelative(5.947f, 6.463f)
						horizontalLineToRelative(-5.947f)
						verticalLineTo(7.135f)
						close()
						moveTo(63.604f, 72.98f)
						curveToRelative(0f, 0.42f, -0.344f, 0.765f, -0.766f, 0.765f)
						horizontalLineToRelative(-47.93f)
						curveToRelative(-0.421f, 0f, -0.763f, -0.345f, -0.763f, -0.765f)
						verticalLineTo(4.768f)
						curveToRelative(0f, -0.421f, 0.342f, -0.762f, 0.763f, -0.762f)
						horizontalLineToRelative(24.413f)
						verticalLineToRelative(1.717f)
						horizontalLineToRelative(-5.18f)
						verticalLineToRelative(2.892f)
						horizontalLineToRelative(5.18f)
						verticalLineToRelative(5.676f)
						horizontalLineToRelative(-5.18f)
						verticalLineToRelative(2.891f)
						horizontalLineToRelative(5.18f)
						verticalLineToRelative(5.442f)
						horizontalLineToRelative(-5.18f)
						verticalLineToRelative(2.891f)
						horizontalLineToRelative(5.18f)
						verticalLineToRelative(0.217f)
						verticalLineToRelative(0.166f)
						verticalLineToRelative(4.281f)
						horizontalLineToRelative(-5.18f)
						verticalLineToRelative(2.892f)
						horizontalLineToRelative(5.18f)
						verticalLineToRelative(5.676f)
						horizontalLineToRelative(-5.18f)
						verticalLineToRelative(2.892f)
						horizontalLineToRelative(5.18f)
						verticalLineToRelative(5.441f)
						horizontalLineToRelative(-5.18f)
						verticalLineToRelative(2.891f)
						horizontalLineToRelative(2.77f)
						curveToRelative(-1.059f, 0.053f, -1.902f, 0.92f, -1.902f, 1.99f)
						verticalLineToRelative(1.141f)
						horizontalLineToRelative(0.016f)
						verticalLineToRelative(8.649f)
						curveToRelative(0f, 2.246f, 1.828f, 4.074f, 4.074f, 4.074f)
						reflectiveCurveToRelative(4.073f, -1.828f, 4.073f, -4.074f)
						verticalLineToRelative(-8.649f)
						horizontalLineToRelative(0.002f)
						verticalLineToRelative(-1.141f)
						curveToRelative(0f, -1.104f, -0.896f, -2f, -2f, -2f)
						horizontalLineToRelative(-0.057f)
						verticalLineToRelative(-4.438f)
						horizontalLineToRelative(4.756f)
						verticalLineToRelative(-2.892f)
						horizontalLineToRelative(-4.756f)
						verticalLineToRelative(-5.441f)
						horizontalLineToRelative(4.756f)
						verticalLineToRelative(-2.892f)
						horizontalLineToRelative(-4.756f)
						verticalLineToRelative(-5.677f)
						horizontalLineToRelative(4.756f)
						verticalLineToRelative(-2.891f)
						horizontalLineToRelative(-4.756f)
						verticalLineToRelative(-4.664f)
						horizontalLineToRelative(4.756f)
						verticalLineToRelative(-2.891f)
						horizontalLineToRelative(-4.756f)
						verticalLineToRelative(-5.442f)
						horizontalLineToRelative(4.756f)
						verticalLineTo(9.842f)
						horizontalLineToRelative(-4.756f)
						verticalLineTo(4.165f)
						horizontalLineToRelative(4.756f)
						verticalLineToRelative(-0.16f)
						horizontalLineToRelative(5.285f)
						verticalLineTo(15.6f)
						curveToRelative(0f, 1.104f, 0.899f, 1.999f, 2.004f, 1.999f)
						horizontalLineToRelative(10.441f)
						verticalLineTo(72.98f)
						close()
						moveTo(41.7f, 56.65f)
						verticalLineToRelative(4.832f)
						curveToRelative(0f, 1.451f, -1.177f, 2.625f, -2.625f, 2.625f)
						curveToRelative(-1.449f, 0f, -2.625f, -1.174f, -2.625f, -2.625f)
						verticalLineTo(56.65f)
						horizontalLineTo(41.7f)
						close()
}
}
}.build()
return _Zip!!
		}

