package com.github.catomon.tsukaremi.util

import com.github.catomon.tsukaremi.ui.windows.WindowConfig

fun setSkikoRenderApi() {
    try {
        if (osName.contains("win")) {
            System.setProperty("skiko.renderApi", "OPENGL")
            echoMsg("skiko.renderApi = OPENGL")
        } else {
            System.setProperty("skiko.renderApi", "SOFTWARE_FAST")
            echoMsg("skiko.renderApi = SOFTWARE_FAST")
        }
        WindowConfig.isTransparent = true
    } catch (e: Exception) {
        WindowConfig.isTransparent = false
        echoWarn("Could not set render api. The window may not have transparency.")
        e.printStackTrace()
    }
}