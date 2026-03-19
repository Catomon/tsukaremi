package com.github.catomon.tsukaremi.ui.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.components.OutlinedText
import com.github.catomon.tsukaremi.ui.effect.Starfall
import com.github.catomon.tsukaremi.ui.modifiers.luckyWindowDecoration
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import com.github.catomon.tsukaremi.ui.util.playSound
import com.github.catomon.tsukaremi.util.canAlwaysOnTop
import com.github.catomon.tsukaremi.util.fromUtcToSystemZoned
import com.github.catomon.tsukaremi.util.toSimpleString
import com.github.panpf.sketch.AsyncImage
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.repeat
import tsukaremi.composeapp.generated.resources.repeat_outline
import tsukaremi.composeapp.generated.resources.star

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

    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            TsukaremiTheme.colors.gradientStart,
            TsukaremiTheme.colors.gradientEnd
        ),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset.Infinite
    )

    Box(modifier.fillMaxSize().background(gradientBrush), contentAlignment = Alignment.Center) {
//        Image(
//            painterResource(Res.drawable.lucky_background_stars),
//            null,
//            Modifier.matchParentSize(),
//            contentScale = ContentScale.Crop,
//            colorFilter = ColorFilter.tint(Color(0x4d9775d5))
//        )

        Starfall(imageResource(Res.drawable.star), fallDuration = 4000)

        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                "assets/c29282c9a734ccddb8a40b2f9eda555c.gif",
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                Modifier.weight(1f).padding(horizontal = 6.dp, vertical = 12.dp)
            ) {
                OutlinedText(
                    reminder.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1, outlineColor = TsukaremiTheme.colors.background
                )

                OutlinedText(
                    reminder.description,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    fontSize = 12.sp,
                    lineHeight = 20.sp, outlineColor = TsukaremiTheme.colors.background
                )
            }

            Column(
                Modifier.fillMaxHeight().padding(end = 12.dp, top = 12.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.End
            ) {
                Row(modifier = Modifier.width(70.dp), horizontalArrangement = Arrangement.End) {
                    if (reminder.isTimer)
                        Box(
                            Modifier.padding(end = 10.dp, top = 2.dp).clickable { onRestart() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(Res.drawable.repeat_outline), null, modifier = Modifier.size(22.dp),
                                tint = TsukaremiTheme.colors.background
                            )

                            Icon(
                                painterResource(Res.drawable.repeat), null, modifier = Modifier.size(16.dp),
                                tint = Color.White
                            )
                        }


                    Box(Modifier.size(24.dp).clickable { onDismiss() }, contentAlignment = Alignment.Center) {
                        OutlinedText(
                            text = "X",
                            outlineColor = TsukaremiTheme.colors.background,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                OutlinedText(
                    remember {
                        reminder.remindAt.fromUtcToSystemZoned().toSimpleString()
                    },
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1, fontSize = 12.sp, outlineColor = TsukaremiTheme.colors.background
                )
            }
        }
    }
}
