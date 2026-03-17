package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.github.catomon.tsukaremi.ui.components.OutlinedText
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import com.github.catomon.tsukaremi.util.openInBrowser
import java.net.URI

@Composable
fun SettingsScreen(
    settings: AppSettings,
    onSettingsChanged: (AppSettings) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hideReminderAfterMillis by remember(settings) { mutableStateOf(settings.hideReminderAfter) }

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
            Column {
                Column {
                    OutlinedText("Dismiss reminder after: ", outlineColor = TsukaremiTheme.colors.background,)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedText("never", outlineColor = TsukaremiTheme.colors.background, fontSize = 12.sp,)
                        RadioButton(hideReminderAfterMillis == 0L, {
                            onSettingsChanged(settings.copy(hideReminderAfter = 0L))
                        })

                        OutlinedText(
                            "5 s.",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp),
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
                            modifier = Modifier.padding(start = 6.dp),
                        )
                        RadioButton(hideReminderAfterMillis == 10_000L, {
                            onSettingsChanged(settings.copy(hideReminderAfter = 10_000L))
                        })

                        OutlinedText(
                            "15 s.",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 6.dp),
                        )
                        RadioButton(hideReminderAfterMillis == 15_000L, {
                            onSettingsChanged(settings.copy(hideReminderAfter = 15_000L))
                        })
                    }
                }
            }

            OutlinedButton({
                openInBrowser(URI.create("https://github.com/Catomon"))
            }, modifier = Modifier.align(Alignment.BottomCenter).padding(all = 6.dp)) {
                OutlinedText(
                    "github.com/catomon",
                    outlineColor = TsukaremiTheme.colors.background,
                    fontSize = 12.sp,
                )
            }
        }

    }
}

