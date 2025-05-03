package com.github.catomon.tsukaremi.ui.compositionlocals

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("LocalNavController not found")
}