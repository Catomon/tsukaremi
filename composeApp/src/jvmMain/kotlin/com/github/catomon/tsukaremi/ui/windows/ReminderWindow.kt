package com.github.catomon.tsukaremi.ui.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.modifiers.luckyWindowDecoration
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import com.github.catomon.tsukaremi.ui.util.playSound
import com.github.catomon.tsukaremi.util.canAlwaysOnTop
import com.github.catomon.tsukaremi.util.epochMillisToSimpleDate
import com.github.panpf.sketch.AsyncImage
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun ReminderWindow(
    reminder: Reminder,
    onRestart: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    state: WindowState = rememberWindowState(size = WindowConfig.reminderWindowSize)
) {
    Window(
        state = state,
        onCloseRequest = onDismiss,
        undecorated = true,
        transparent = true,
        alwaysOnTop = canAlwaysOnTop(),
        resizable = false
    ) {
        window.focusableWindowState = false
        window.isFocusable = false

        TsukaremiTheme {
            ReminderWindowContent(reminder, onRestart, onDismiss, modifier.fillMaxSize())
        }
    }
}

@Composable
private fun ReminderWindowContent(
    reminder: Reminder,
    onRestart: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) = Surface(modifier.luckyWindowDecoration()) {

    LaunchedEffect(Unit) {
        playSound("se_mop.wav")
    }

    Row {
        AsyncImage("assets/c29282c9a734ccddb8a40b2f9eda555c.gif", contentDescription = null)

        Column(
            Modifier.background(MaterialTheme.colorScheme.background.copy(0.9f)).weight(1f).padding(horizontal = 6.dp)
        ) {
            Text(
                reminder.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                reminder.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                fontSize = 12.sp,
                lineHeight = 20.sp
            )
            Spacer(Modifier.weight(1f))
            Box(Modifier.fillMaxWidth()) {
                Text(
                    remember {
                        epochMillisToSimpleDate(run {
                            val remindAt: LocalDateTime = reminder.remindAt
                            val zoneId = ZoneId.systemDefault()
                            val zonedDateTime = remindAt.atZone(zoneId)
                            val offset: ZoneOffset = zonedDateTime.offset
                            remindAt.toEpochSecond(offset) * 1000
                        })
                    }, modifier = Modifier.align(Alignment.BottomStart),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1, fontSize = 12.sp
                )
            }
        }

        if (reminder.isTimer)
            TextButton(onRestart, modifier = Modifier.fillMaxHeight().width(24.dp)) {
                Text("🔁", modifier = Modifier.offset((-8).dp))
            }

        TextButton(onDismiss, modifier = Modifier.fillMaxHeight().width(24.dp)) {
            Text("X", modifier = Modifier.offset((-4).dp))
        }
    }
}
