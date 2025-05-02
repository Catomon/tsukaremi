package com.github.catomon.tsukaremi.util

import androidx.compose.ui.graphics.Color

fun Color.darken(factor: Float = 0.8f): Color {
    return Color(
        red = (red * factor).coerceIn(0f, 1f),
        green = (green * factor).coerceIn(0f, 1f),
        blue = (blue * factor).coerceIn(0f, 1f),
        alpha = alpha
    )
}