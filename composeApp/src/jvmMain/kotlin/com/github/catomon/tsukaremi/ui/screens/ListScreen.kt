package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import com.github.catomon.tsukaremi.ui.util.darken
import com.github.catomon.tsukaremi.ui.util.rememberLazyListStateHijacker
import com.github.catomon.tsukaremi.util.fromUtcToSystemZoned
import com.github.catomon.tsukaremi.util.toSimpleString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.lucky_background_stars
import tsukaremi.composeapp.generated.resources.pencil
import tsukaremi.composeapp.generated.resources.repeat
import tsukaremi.composeapp.generated.resources.trash
import java.time.ZoneOffset

@Composable
fun ListScreen(
    reminders: List<Reminder>,
    onEdit: (Reminder) -> Unit,
    onRestart: (Reminder) -> Unit,
    onDelete: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    val (oldReminders, incomingReminders) = remember(reminders) {
        reminders.sortedByDescending { it.remindAt.toInstant(ZoneOffset.UTC).epochSecond }.groupBy { it.isCompleted }
            .let { (it[true] ?: emptyList()) to (it[false]?.reversed() ?: emptyList()) }
    }

    val listState = rememberLazyListState()
    rememberLazyListStateHijacker(listState = listState)
    val scrollbarAdapter = rememberScrollbarAdapter(listState)

    Box(modifier = modifier.fillMaxSize()) {
//        Image(
//            painterResource(Res.drawable.lucky_background_stars),
//            null,
//            modifier.matchParentSize(),
//            contentScale = ContentScale.Crop,
//            colorFilter = ColorFilter.tint(Color(0x4d9775d5))
//        )

        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .matchParentSize()
//                .graphicsLayer {
//                    compositingStrategy = CompositingStrategy.Offscreen
//                }
//                .drawWithContent {
//                    drawContent()
//                    drawRect(TsukaremiTheme.colors.background, size = size, blendMode = BlendMode.SrcOut)
//                    drawContent()
//                }
        ) {
            LazyColumn(
                state = listState,
                // contentPadding = PaddingValues(2.dp),
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp))
            ) {
                items(incomingReminders, key = { it.id }) {
                    ReminderListItem(it, onEdit, onDelete, onRestart, Modifier.animateItem())
                }

                if (oldReminders.isNotEmpty()) {
                    item(null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.inversePrimary.copy(0.75f),
                                thickness = 2.dp,
                                modifier = Modifier.padding(top = 20.dp)
                            )

                            Text(
                                "Expired",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                        }

                    }

                    items(oldReminders, key = { it.id }) {
                        ReminderListItem(it, onEdit, onDelete, onRestart, Modifier.animateItem())
                    }
                }

                item {
                    Spacer(Modifier.height(64.dp))
                }
            }

            VerticalScrollbar(
                adapter = scrollbarAdapter, modifier = Modifier.fillMaxHeight().align(
                    Alignment.CenterEnd
                ).clickable { })
        }
    }
}

@Composable
fun ReminderListItem(
    reminder: Reminder,
    onEdit: (Reminder) -> Unit,
    onRemove: (Reminder) -> Unit,
    onRestart: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered = interactionSource.collectIsHoveredAsState()

    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            TsukaremiTheme.colors.gradientStart.copy(alpha = 0.35f),
            Color(0x00ff5dde)
        ),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset.Infinite
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 4.dp)
            .background(
                brush = gradientBrush,
                shape = RoundedCornerShape(8.dp),
            )
//            .border(
//                width = 3.dp,
//                color = TsukaremiTheme.colors.background,
//                shape = RoundedCornerShape(8.dp)
//            )

            .padding(8.dp)
            .alpha(if (reminder.isCompleted) 0.75f else 1f)
            .hoverable(interactionSource),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Title
            Box {
                Text(
                    reminder.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style =
                        LocalTextStyle.current.merge(
                            TextStyle(
                                color =  TsukaremiTheme.colors.background.darken(),
                                drawStyle = Stroke(
                                    width = 6f,
                                    join = StrokeJoin.Round
                                )
                            )

                        )
                )
                Text(
                    reminder.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }

// Description
            Box {
                Text(
                    reminder.description,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style =
                        LocalTextStyle.current.merge(
                            TextStyle(
                                color =  TsukaremiTheme.colors.background.darken(),
                                drawStyle = Stroke(
                                    width = 4f,
                                    join = StrokeJoin.Round
                                )
                            )

                        )
                )
                Text(
                    reminder.description,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }

// Date
            Box {
                remember(reminder) {
                    reminder.remindAt.fromUtcToSystemZoned().toSimpleString()
                }.let { text ->
                    Text(
                        text,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style =
                            LocalTextStyle.current.merge(
                                TextStyle(
                                    color =  TsukaremiTheme.colors.background.darken(),
                                    drawStyle = Stroke(
                                        width = 4f,
                                        join = StrokeJoin.Round
                                    )
                                )

                            )
                    )
                    Text(
                        text,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                }
            }
        }

        AnimatedVisibility(isHovered.value, enter = fadeIn(), exit = fadeOut()) {
            RemItemButtons(onEdit, reminder, onRestart, onRemove)
        }
    }
}

@Composable
private fun RemItemButtons(
    onEdit: (Reminder) -> Unit,
    reminder: Reminder,
    onRestart: (Reminder) -> Unit,
    onRemove: (Reminder) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(26.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton({
            onEdit(reminder)
        }, modifier = Modifier.size(25.dp)) {
            Icon(painterResource(Res.drawable.pencil), null, modifier = Modifier.size(16.dp), tint = TsukaremiTheme.colors.background.darken())
        }

        val scale = remember { Animatable(1f) }

        if (reminder.isTimer)
            IconButton({
                coroutineScope.launch {
                    scale.animateTo(
                        1.25f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    )

                    scale.animateTo(
                        1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    )
                }
                onRestart(reminder)
            }, modifier = Modifier.scale(scale.value).size(25.dp)) {
                Icon(painterResource(Res.drawable.repeat), null, modifier = Modifier.size(16.dp),
                    tint = TsukaremiTheme.colors.background.darken())
            }

        IconButton({
            onRemove(reminder)
        }, modifier = Modifier.size(25.dp)) {
            Icon(painterResource(Res.drawable.trash), null, modifier = Modifier.size(16.dp), tint = TsukaremiTheme.colors.background.darken())
        }
    }
}