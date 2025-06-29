package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.catomon.tsukaremi.data.local.AppSettings
import com.github.catomon.tsukaremi.util.openInBrowser
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.lucky_background_stars
import java.net.URI

@Serializable
object SettingsDestination

@Composable
fun SettingsScreen(
    settings: AppSettings,
    onSettingsChanged: (AppSettings) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hideReminderAfterMillis by remember(settings) { mutableStateOf(settings.hideReminderAfter) }

    Surface(modifier) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painterResource(Res.drawable.lucky_background_stars),
                null,
                modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color(0xff9775d5))
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.matchParentSize().background(MaterialTheme.colorScheme.background.copy(0.75f))
            ) {
                Column {
                    Column {
                        Text("Dismiss reminder after: ")
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("never", fontSize = 12.sp)
                            RadioButton(hideReminderAfterMillis == 0L, {
                                onSettingsChanged(settings.copy(hideReminderAfter = 0L))
                            })

                            Text("5 sec.", modifier = Modifier.padding(start = 6.dp), fontSize = 12.sp)
                            RadioButton(hideReminderAfterMillis == 5_000L, {
                                onSettingsChanged(settings.copy(hideReminderAfter = 5_000L))
                            })

                            Text("10 sec.", modifier = Modifier.padding(start = 6.dp), fontSize = 12.sp)
                            RadioButton(hideReminderAfterMillis == 10_000L, {
                                onSettingsChanged(settings.copy(hideReminderAfter = 10_000L))
                            })

                            Text("15 sec.", modifier = Modifier.padding(start = 6.dp), fontSize = 12.sp)
                            RadioButton(hideReminderAfterMillis == 15_000L, {
                                onSettingsChanged(settings.copy(hideReminderAfter = 15_000L))
                            })
                        }
                    }
                }

                Text("github.com/catomon", modifier = Modifier.align(Alignment.BottomCenter).padding(all = 6.dp).clickable {
                    openInBrowser(URI.create("https://github.com/Catomon"))
                }, fontSize = 12.sp)
            }
        }
    }
}
