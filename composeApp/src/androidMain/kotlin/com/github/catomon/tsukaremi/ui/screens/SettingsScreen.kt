package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.catomon.tsukaremi.data.local.AppSettings
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import org.jetbrains.compose.resources.painterResource
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.lucky_background_stars

@Composable
fun SettingsScreen(
    navBack: () -> Unit,
    settings: AppSettings,
    onSettingsChanged: (AppSettings) -> Unit,
    modifier: Modifier = Modifier,
    padding: PaddingValues,
) {
    val hideReminderAfterMillis by remember(settings) { mutableLongStateOf(settings.hideReminderAfter) }

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
                modifier = Modifier
                    .matchParentSize()
                    .background(TsukaremiTheme.colors.background)
                    .padding(bottom = padding.calculateBottomPadding())
            ) {
                Column {
                    Column {
                        Text("Dismiss reminder after: ")
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("never", fontSize = 12.sp)
                            RadioButton(hideReminderAfterMillis == 0L, {
                                onSettingsChanged(settings.copy(hideReminderAfter = 0L))
                            })

                            Text("5 s.", modifier = Modifier.padding(start = 6.dp), fontSize = 12.sp)
                            RadioButton(hideReminderAfterMillis == 5_000L, {
                                onSettingsChanged(settings.copy(hideReminderAfter = 5_000L))
                            })

                            Text("10 s.", modifier = Modifier.padding(start = 6.dp), fontSize = 12.sp)
                            RadioButton(hideReminderAfterMillis == 10_000L, {
                                onSettingsChanged(settings.copy(hideReminderAfter = 10_000L))
                            })

                            Text("15 s.", modifier = Modifier.padding(start = 6.dp), fontSize = 12.sp)
                            RadioButton(hideReminderAfterMillis == 15_000L, {
                                onSettingsChanged(settings.copy(hideReminderAfter = 15_000L))
                            })
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    val urlHandler = LocalUriHandler.current
                    OutlinedButton(
                        {
                            urlHandler.openUri("https://github.com/Catomon")
                        }
                    ) {
                        Text(
                            "github.com/catomon",
                            fontSize = 12.sp
                        )
                    }

                    Button(navBack, shape = CircleShape, modifier = Modifier.padding(4.dp)) {
                        Text("Save & return")
                    }
                }
            }
        }
    }
}
