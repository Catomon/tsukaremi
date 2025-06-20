package com.github.catomon.tsukaremi.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File

fun ApplicationScope.setComposeExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        File("last_error.txt").writeText(e.stackTraceToString())
        e.printStackTrace()

        exitApplication()

        application {
            Window(
                onCloseRequest = ::exitApplication,
                state = rememberWindowState(width = 300.dp, height = 250.dp),
                visible = true,
                title = "Error",
            ) {
                val clipboard = LocalClipboardManager.current

                Box(contentAlignment = Alignment.Center) {
                    Text(e.stackTraceToString(), Modifier.fillMaxSize())
                    Button({
                        clipboard.setText(AnnotatedString(e.stackTraceToString()))
                    }, Modifier.align(Alignment.BottomCenter)) {
                        Text("Copy")
                    }
                }
            }
        }
    }
}