package com.github.catomon.tsukaremi.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.util.rememberLazyListStateHijacker
import com.github.catomon.tsukaremi.util.fromUtcToSystemZoned
import com.github.catomon.tsukaremi.util.toSimpleString
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.lucky_background_stars
import tsukaremi.composeapp.generated.resources.pencil
import tsukaremi.composeapp.generated.resources.repeat
import tsukaremi.composeapp.generated.resources.trash
import java.time.ZoneOffset

@Serializable
object ListDestination

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListScreen(
    reminders: List<Reminder>,
    onCreateNew: () -> Unit,
    onEdit: (Reminder) -> Unit,
    onRestart: (Reminder) -> Unit,
    onDelete: (Reminder) -> Unit,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(Dp.Unspecified)
) {
    val (oldReminders, incomingReminders) = remember(reminders) {
        reminders.sortedByDescending { it.remindAt.toInstant(ZoneOffset.UTC).epochSecond }.groupBy { it.isCompleted }
            .let { (it[true] ?: emptyList()) to (it[false] ?: emptyList()) }
    }

    val listState = rememberLazyListState()

    rememberLazyListStateHijacker(listState = listState)

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painterResource(Res.drawable.lucky_background_stars),
            null,
            modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color(0xff9775d5))
        )

        val backgroundColor = MaterialTheme.colorScheme.background.copy(0.75f)

        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .matchParentSize()
                .graphicsLayer() {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    drawContent()
                    drawRect(backgroundColor, size = size, blendMode = BlendMode.SrcOut)
                    drawContent()
                }
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .padding(start = 6.dp, end = 6.dp)
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

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .padding(bottom = padding.calculateBottomPadding())
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = onCreateNew) {
                    Text("New Reminder")
                }
            }
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
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(4.dp)
            .background(color = MaterialTheme.colorScheme.inversePrimary.copy(0.75f), shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .alpha(if (reminder.isCompleted) 0.75f else 1f)
            .hoverable(interactionSource),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(0.55f)) {
            Text(reminder.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(reminder.description, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(remember(reminder) {
                reminder.remindAt.fromUtcToSystemZoned().toSimpleString()
            }, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.weight(.45f)
        ) {
            IconButton({
                onEdit(reminder)
            }, modifier = Modifier) {
                Icon(painterResource(Res.drawable.pencil), null, modifier = Modifier.size(20.dp))
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
                }, modifier = Modifier.scale(scale.value)) {
                    Icon(painterResource(Res.drawable.repeat), null, modifier = Modifier.size(20.dp))
                }

            IconButton({
                onRemove(reminder)
            }) {
                Icon(painterResource(Res.drawable.trash), null, modifier = Modifier.size(20.dp))
            }
        }
    }
}
