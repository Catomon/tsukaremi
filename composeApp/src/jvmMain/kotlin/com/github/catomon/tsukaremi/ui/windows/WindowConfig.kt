package com.github.catomon.tsukaremi.ui.windows

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

object WindowConfig {
    val title: String = "Tsukaremi"

    const val WIDTH = 400
    const val HEIGHT = 700

    val reminderWindowSize = DpSize(350.dp, 125.dp)

    var isTransparent = true

    val isTraySupported = androidx.compose.ui.window.isTraySupported
}
