package com.github.catomon.tsukaremi.ui.windows

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.components.OutlinedText
import com.github.catomon.tsukaremi.ui.effect.Starfall
import com.github.catomon.tsukaremi.ui.modifiers.blurredShadow
import com.github.catomon.tsukaremi.ui.modifiers.customShadow
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
) = Surface(modifier.luckyReminderWindowDecoration()) {

    LaunchedEffect(Unit) {
        playSound("se_mop.wav")
    }

    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            TsukaremiTheme.colors.gradientStart,
            TsukaremiTheme.colors.gradientEnd
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    val gradientBrush2 = Brush.linearGradient(
        colors = listOf(
            TsukaremiTheme.colors.gradientStart.copy(alpha = 0.35f),
            Color(0x00ff5dde)
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    val interactionSource = remember { MutableInteractionSource() }

    val isHovered by interactionSource.collectIsHoveredAsState()

    Box(
        modifier.fillMaxSize().background(gradientBrush).hoverable(interactionSource),
        contentAlignment = Alignment.Center
    ) {
//        Image(
//            painterResource(Res.drawable.lucky_background_stars),
//            null,
//            Modifier.matchParentSize(),
//            contentScale = ContentScale.Crop,
//            colorFilter = ColorFilter.tint(Color(0x4d9775d5))
//        )

        Starfall(imageResource(Res.drawable.star), fallDuration = 4000, starCount = 12)

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(gradientBrush2)) {
            AsyncImage(
                "assets/c29282c9a734ccddb8a40b2f9eda555c.gif",
                contentDescription = null,
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                Modifier.weight(1f).padding(horizontal = 6.dp, vertical = 12.dp).verticalScroll(remember { ScrollState(0)})
            ) {
                OutlinedText(
                    reminder.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1, outlineColor = TsukaremiTheme.colors.componentBorder
                )

                if (reminder.description.isNotEmpty())
                    OutlinedText(
                        reminder.description,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 32,
                        fontSize = 12.sp,
                        lineHeight = 20.sp, outlineColor = TsukaremiTheme.colors.componentBorder
                    )
            }
        }

        DismissButton(onDismiss, Modifier.align(Alignment.TopEnd).padding(end = 12.dp, top = 12.dp))

        Box(
            modifier = Modifier.fillMaxHeight().padding(end = 6.dp, top = 12.dp, bottom = 12.dp).align(Alignment.BottomEnd),
            contentAlignment = Alignment.BottomEnd
        ) {
            AnimatedContent(isHovered, transitionSpec = {
                (fadeIn(animationSpec = tween(220, delayMillis = 90)))
                    .togetherWith(fadeOut(animationSpec = tween(90)))
            }, modifier = Modifier.width(50.dp), contentAlignment = Alignment.CenterEnd) { isHovered ->
                if (isHovered && reminder.isTimer) {
                    Box(
                        Modifier.clickable { onRestart() }.offset(9.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painterResource(Res.drawable.repeat_outline),
                            null,
                            modifier = Modifier.size(22.dp),
                            tint = TsukaremiTheme.colors.background
                        )

                        Icon(
                            painterResource(Res.drawable.repeat), null, modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                    }
                } else {
                    OutlinedText(
                        remember {
                            reminder.remindAt.fromUtcToSystemZoned().toSimpleString().split(" ").lastOrNull() ?: ""
                        },
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1, fontSize = 12.sp, outlineColor = TsukaremiTheme.colors.background
                    )
                }
            }
        }
    }
}

@Composable
private fun DismissButton(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(14.dp)
            .background(color = TsukaremiTheme.colors.background, shape = CircleShape).clickable { onDismiss() }
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier.size(8.dp)
                .background(color = Color.White, shape = CircleShape)
        )
    }

//    Box(Modifier.size(24.dp).clickable { onDismiss() }, contentAlignment = Alignment.Center) {
//        OutlinedText(
//            text = "X",
//            outlineColor = TsukaremiTheme.colors.componentBorder,
//            fontSize = 16.sp
//        )
//    }
}

@Composable
fun Modifier.luckyReminderWindowDecoration(): Modifier {
    val density = LocalDensity.current
    val shadowColor = Color.White
    val glowColor = MaterialTheme.colorScheme.surfaceContainerLow
    val borderGradient = Brush.horizontalGradient(
        0.0f to TsukaremiTheme.colors.windowBorderGradientStart,
        0.15f to TsukaremiTheme.colors.windowBorderGradientStart,
        0.5f to TsukaremiTheme.colors.windowBorderGradientEnd,
        1.0f to TsukaremiTheme.colors.windowBorderGradientEnd,
    )

    return if (WindowConfig.isTransparent)
        this.padding(8.dp)
            .border(
                width = 4.dp,
                brush = borderGradient,
                shape = RoundedCornerShape(8.dp)
            )
            .customShadow(color = Color.White).clip(RoundedCornerShape(8.dp))
    else
        this.blurredShadow(cornerRadius = 0.dp, color = Color.White)
            .border(
                width = 2.dp,
                color = shadowColor,
                shape = RectangleShape
            )
}