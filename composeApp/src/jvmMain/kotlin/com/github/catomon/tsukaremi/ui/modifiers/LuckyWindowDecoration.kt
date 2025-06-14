package com.github.catomon.tsukaremi.ui.modifiers

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.catomon.tsukaremi.ui.windows.WindowConfig
import com.github.catomon.tsukaremi.ui.util.darken
import org.jetbrains.skia.FilterBlurMode
import org.jetbrains.skia.MaskFilter

@Composable
fun Modifier.luckyWindowDecoration(): Modifier {
    val density = LocalDensity.current
    val shadowColor = MaterialTheme.colorScheme.background.darken(0.7f)
    val glowColor = MaterialTheme.colorScheme.surfaceContainerLow
    return if (WindowConfig.isTransparent)
        this.padding(8.dp).customShadow().drawBehind {
            val radDp = with(density) { 12.dp.toPx() }
//            drawRoundRect(
//                color = glowColor,
//                topLeft = Offset(0f, -2f),
//                size = this.size.copy(),
//                cornerRadius = CornerRadius(radDp)
//            )
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(0f, with(density) { 5.dp.toPx() }),
                size = this.size.copy(),
                cornerRadius = CornerRadius(radDp)
            )
        }.clip(RoundedCornerShape(8.dp))
    else
        this.blurredShadow(cornerRadius = 0.dp)
            .border(
                width = 2.dp,
                color = shadowColor,
                shape = RectangleShape
            )
}

fun Modifier.customShadow(
    color: Color = Color.Black,
    alpha: Float = 0.75f,
    cornerRadius: Dp = 12.dp,
    shadowRadius: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp
) = drawBehind {
    val shadowColor = color.copy(alpha = alpha).toArgb()

    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            asFrameworkPaint().apply {
                this.color = shadowColor
                maskFilter = MaskFilter.makeBlur(
                    FilterBlurMode.NORMAL,
                    shadowRadius.toPx()
                )
            }
        }

        canvas.drawRoundRect(
            left = offsetX.toPx(),
            top = offsetY.toPx(),
            right = size.width + offsetX.toPx(),
            bottom = size.height + offsetY.toPx(),
            cornerRadius.toPx(),
            cornerRadius.toPx(),
            paint
        )
    }
}