package com.github.catomon.tsukaremi.ui.compositionlocals

import androidx.compose.runtime.compositionLocalOf
import com.github.catomon.tsukaremi.data.local.AppSettings

val LocalAppSettings = compositionLocalOf<AppSettings> {
    error("LocalAppSettings not found")
}