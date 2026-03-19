package com.github.catomon.tsukaremi.ui.windows

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

object WindowConfig {
    val title: String = "Tsukaremi"

    const val WIDTH = 300
    const val HEIGHT = 500

    val reminderWindowSize = DpSize(350.dp, 125.dp)

    var isTransparent = true

    val isTraySupported = androidx.compose.ui.window.isTraySupported
}
