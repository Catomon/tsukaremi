package com.github.catomon.tsukaremi.ui.compositionlocals

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.awt.ComposeWindow

val LocalWindow = compositionLocalOf<ComposeWindow> {
    error("No window")
}