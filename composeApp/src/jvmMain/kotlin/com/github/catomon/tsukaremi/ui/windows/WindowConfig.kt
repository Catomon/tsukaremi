package com.github.catomon.tsukaremi.ui.windows

object WindowConfig {
    val title: String = "Tsukaremi"

    const val WIDTH = 400
    const val HEIGHT = 700

    var isTransparent = true

    val isTraySupported = androidx.compose.ui.window.isTraySupported
}
