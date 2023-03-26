package uk.ac.aber.cs31620.learningapp.ui.icons.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import uk.ac.aber.cs31620.learningapp.ui.icons.MyIconPack

public val MyIconPack.Quiz: ImageVector
    get() {
        if (_quiz != null) {
            return _quiz!!
        }
        _quiz = Builder(name = "Quiz", defaultWidth = 49.869476.dp, defaultHeight = 39.485783.dp,
                viewportWidth = 49.869476f, viewportHeight = 39.485783f).apply {
            path(fill = SolidColor(Color(0xFF050505)), stroke = null, strokeLineWidth = 0.39316f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.4359f, 39.0656f)
                curveTo(11.2565f, 37.7874f, 4.8322f, 33.2923f, 2.2139f, 28.4147f)
                curveTo(-2.8821f, 18.9215f, 1.0845f, 7.8178f, 11.3881f, 2.7335f)
                curveTo(22.5993f, -2.7987f, 36.1603f, 0.302f, 42.7523f, 9.9048f)
                curveToRelative(2.1275f, 3.0992f, 2.9438f, 5.8431f, 2.9438f, 9.895f)
                curveToRelative(0.0f, 4.1291f, -0.8708f, 6.9778f, -3.0678f, 10.0359f)
                lineToRelative(-1.3918f, 1.9373f)
                lineToRelative(2.8432f, 0.8298f)
                curveToRelative(1.5638f, 0.4564f, 3.2855f, 0.9612f, 3.8261f, 1.1218f)
                curveToRelative(0.5406f, 0.1606f, 1.2483f, 0.7644f, 1.5726f, 1.3417f)
                curveToRelative(0.5264f, 0.937f, 0.5239f, 1.1657f, -0.0237f, 2.1309f)
                curveToRelative(-0.558f, 0.9833f, -0.8071f, 1.0769f, -2.7521f, 1.0339f)
                curveToRelative(-1.2444f, -0.0275f, -3.7195f, -0.5753f, -5.9193f, -1.31f)
                lineToRelative(-3.7807f, -1.2627f)
                lineToRelative(-2.3133f, 1.1675f)
                curveToRelative(-1.2723f, 0.6421f, -3.2864f, 1.461f, -4.4757f, 1.8196f)
                curveToRelative(-3.037f, 0.9159f, -9.3735f, 1.1243f, -12.7777f, 0.4201f)
                close()
                moveTo(29.6971f, 33.7058f)
                lineTo(32.1328f, 32.9535f)
                lineTo(29.7972f, 30.6821f)
                curveToRelative(-2.5693f, -2.4986f, -2.9521f, -3.7787f, -1.5493f, -5.1815f)
                curveToRelative(1.1719f, -1.1719f, 2.4178f, -0.9742f, 3.9528f, 0.6273f)
                curveToRelative(0.7452f, 0.7775f, 2.1372f, 1.9569f, 3.0934f, 2.6209f)
                lineToRelative(1.7385f, 1.2073f)
                lineToRelative(1.3036f, -1.5397f)
                curveToRelative(2.3605f, -2.7881f, 3.054f, -4.6906f, 3.0691f, -8.42f)
                curveToRelative(0.0167f, -4.1236f, -0.9145f, -6.2983f, -3.9885f, -9.3147f)
                curveTo(28.4308f, 1.8644f, 10.6351f, 4.0856f, 5.4219f, 14.6752f)
                curveToRelative(-1.7447f, 3.5441f, -1.7447f, 7.0984f, 0.0f, 10.6424f)
                curveToRelative(3.7481f, 7.6135f, 14.6202f, 11.3703f, 24.2752f, 8.3882f)
                close()
            }
        }
        .build()
        return _quiz!!
    }

private var _quiz: ImageVector? = null
