package com.github.catomon.tsukaremi.util

import javax.swing.JOptionPane

fun setDefaultExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        JOptionPane.showMessageDialog(
            null,
            e.stackTraceToString(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        )
    }
}