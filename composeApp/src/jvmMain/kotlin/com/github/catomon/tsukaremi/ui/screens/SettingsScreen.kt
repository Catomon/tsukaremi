package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.catomon.tsukaremi.data.local.AppSettings
import com.github.catomon.tsukaremi.data.local.ReminderMode
import com.github.catomon.tsukaremi.data.local.WindowBehavior
import com.github.catomon.tsukaremi.ui.components.OutlinedText
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import com.github.catomon.tsukaremi.util.openInBrowser
import java.net.URI
import kotlin.system.exitProcess

@Composable
fun SettingsScreen(
    settings: AppSettings,
    onSettingsChanged: (AppSettings) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hideReminderAfterMillis by remember(settings) { mutableStateOf(settings.hideReminderAfter) }
    val reminderMode by remember(settings) { mutableStateOf(settings.reminderMode) }
    val windowBehavior by remember(settings) { mutableStateOf(settings.windowBehavior) }
    val showEffects by remember(settings) { mutableStateOf(settings.showEffects) }

    Box(modifier = modifier.fillMaxSize()) {
        //            Image(
//                painterResource(Res.drawable.lucky_background_stars),
//                null,
//                modifier.matchParentSize(),
//                contentScale = ContentScale.Crop,
//                colorFilter = ColorFilter.tint(Color(0xff9775d5))
//            )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.matchParentSize()
        ) {
            val scrollState = rememberScrollState(0)
            Column(Modifier.verticalScroll(scrollState).padding(top = 8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(bottom = 2.dp)
                ) {
                    OutlinedButton({ exitProcess(0) }) {
                        OutlinedText("Exit App", outlineColor = TsukaremiTheme.colors.background, fontSize = 12.sp)
                    }

                    OutlinedButton({
                        openInBrowser(URI.create("https://github.com/Catomon"))
                    }) {
                        OutlinedText(
                            "github: catomon",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp
                        )
                    }
                }

                // Show effects
                Column {
                    OutlinedText("Effects: ", outlineColor = TsukaremiTheme.colors.background)

                    Row {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedText(
                                "enabled",
                                outlineColor = TsukaremiTheme.colors.background,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                            RadioButton(showEffects, {
                                onSettingsChanged(settings.copy(showEffects = true))
                            })
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedText(
                                "disabled",
                                outlineColor = TsukaremiTheme.colors.background,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                            RadioButton(!showEffects, {
                                onSettingsChanged(settings.copy(showEffects = false))
                            })
                        }
                    }
                }

                // Hide reminder after
                Column {
                    OutlinedText("Dismiss reminder after: ", outlineColor = TsukaremiTheme.colors.background)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedText(
                            "never", outlineColor = TsukaremiTheme.colors.background, fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        RadioButton(hideReminderAfterMillis == 0L, {
                            onSettingsChanged(settings.copy(hideReminderAfter = 0L))
                        })
                        OutlinedText(
                            "5 s.",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        RadioButton(hideReminderAfterMillis == 5_000L, {
                            onSettingsChanged(settings.copy(hideReminderAfter = 5_000L))
                        })
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedText(
                            "10 s.",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        RadioButton(hideReminderAfterMillis == 10_000L, {
                            onSettingsChanged(settings.copy(hideReminderAfter = 10_000L))
                        })
                        OutlinedText(
                            "15 s.",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        RadioButton(hideReminderAfterMillis == 15_000L, {
                            onSettingsChanged(settings.copy(hideReminderAfter = 15_000L))
                        })
                    }
                }

                // Reminder mode
                Column(modifier = Modifier.padding(top = 24.dp)) {
                    OutlinedText("Reminder mode: ", outlineColor = TsukaremiTheme.colors.background)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedText(
                            "with sound", outlineColor = TsukaremiTheme.colors.background, fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        RadioButton(reminderMode == ReminderMode.WITH_SOUND, {
                            onSettingsChanged(settings.copy(reminderMode = ReminderMode.WITH_SOUND))
                        })
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedText(
                            "window only",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        RadioButton(reminderMode == ReminderMode.WITHOUT_SOUND, {
                            onSettingsChanged(settings.copy(reminderMode = ReminderMode.WITHOUT_SOUND))
                        })
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedText(
                            "sound only",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        RadioButton(reminderMode == ReminderMode.SOUND_ONLY, {
                            onSettingsChanged(settings.copy(reminderMode = ReminderMode.SOUND_ONLY))
                        })
                    }
                }

                // Window behavior
                Column(modifier = Modifier.padding(top = 24.dp)) {
                    OutlinedText("Reminder window behavior: ", outlineColor = TsukaremiTheme.colors.background)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedText(
                            "always on top",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        RadioButton(windowBehavior == WindowBehavior.ALWAYS_ON_TOP, {
                            onSettingsChanged(settings.copy(windowBehavior = WindowBehavior.ALWAYS_ON_TOP))
                        })
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedText(
                            "normal window",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        RadioButton(windowBehavior == WindowBehavior.NORMAL_WINDOW, {
                            onSettingsChanged(settings.copy(windowBehavior = WindowBehavior.NORMAL_WINDOW))
                        })
                    }
                }

                Spacer(Modifier.height(120.dp))
            }
        }
    }
}


