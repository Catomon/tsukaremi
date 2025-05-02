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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.github.catomon.tsukaremi.ui.windows.WindowConfig
import com.github.catomon.tsukaremi.util.darken

@Composable
fun Modifier.luckyWindowDecoration(): Modifier {
    val density = LocalDensity.current
    val shadowColor = MaterialTheme.colorScheme.surface.darken(0.8f)
    val blinkShadow = MaterialTheme.colorScheme.onSurface.darken(0.5f)
    return if (WindowConfig.isTransparent)
        this
            .padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
            .drawBehind {
                drawRoundRect(
                    color = blinkShadow,
                    topLeft = Offset(0f, -2f),
                    size = this.size.copy(),
                    cornerRadius = CornerRadius(12f)
                )
            }
            .drawBehind {
                drawRoundRect(
                    color = shadowColor,
                    topLeft = Offset(0f, with(density) { 2.dp.toPx() }),
                    size = this.size.copy(),
                    cornerRadius = CornerRadius(12f)
                )
            }
            .blurredShadow(addHeight = 2.dp)
            .clip(RoundedCornerShape(12.dp))
    else
        this.blurredShadow(cornerRadius = 0.dp)
            .border(
                width = 2.dp,
                color = shadowColor,
                shape = RectangleShape
            )
}