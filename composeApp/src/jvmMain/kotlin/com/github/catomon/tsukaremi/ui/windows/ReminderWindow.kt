package com.github.catomon.tsukaremi.ui.windows

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.modifiers.luckyWindowDecoration
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import java.time.LocalDateTime

@Composable
fun ReminderWindow(
    reminder: Reminder,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    state: WindowState = rememberWindowState(size = WindowConfig.reminderWindowSize)
) {
    Window(
        state = state,
        onCloseRequest = onDismiss,
        undecorated = true,
        transparent = true,
        alwaysOnTop = false,
        resizable = false
    ) {
        TsukaremiTheme {
            ReminderWindowContent(reminder, onDismiss, modifier.fillMaxSize())
        }
    }
}

@Composable
private fun ReminderWindowContent(
    reminder: Reminder,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) = Surface(modifier.luckyWindowDecoration()) {
    Column(Modifier.padding(horizontal = 6.dp)) {
        Text(reminder.title)
        Text(reminder.description)
        Spacer(Modifier.weight(1f))
        Box(Modifier.fillMaxWidth()) {
            Text(reminder.remindAt.toString(), modifier = Modifier.align(Alignment.BottomStart))
            Button({
                onDismiss()
            }, modifier = Modifier.align(Alignment.BottomEnd)) {
                Text("Dismiss")
            }
        }
    }
}

@Preview
@Composable
fun ReminderWindowPreview() {
    TsukaremiTheme {
        ReminderWindowContent(
            Reminder(
                id = 0,
                title = "title",
                description = "description",
                remindAt = LocalDateTime.now()
            ),
            onDismiss = {

            },
            modifier = Modifier.size(WindowConfig.reminderWindowSize),
        )
    }
}