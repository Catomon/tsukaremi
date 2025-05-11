package com.github.catomon.tsukaremi.ui.util

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import java.awt.GraphicsEnvironment
import java.awt.Rectangle

fun getBottomRightPosition(windowSize: DpSize): WindowPosition {
    val maxBounds: Rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds

    val posX = maxBounds.x + maxBounds.width - windowSize.width.value.toInt()
    val posY = maxBounds.y + maxBounds.height - windowSize.height.value.toInt()

    return WindowPosition(posX.dp, posY.dp)
}