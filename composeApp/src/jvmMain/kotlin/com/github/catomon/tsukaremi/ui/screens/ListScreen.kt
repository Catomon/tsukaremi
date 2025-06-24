package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.util.epochMillisToSimpleDate
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Serializable
object ListDestination

@Composable
fun ListScreen(
    reminders: List<Reminder>,
    onCreateNew: () -> Unit,
    onEdit: (Reminder) -> Unit,
    onRestart: (Reminder) -> Unit,
    onDelete: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    val (oldReminders, incomingReminders) = remember(reminders) {
        reminders.groupBy { it.isCompleted }
            .let { (it[true]?.reversed() ?: emptyList()) to (it[false]?.reversed() ?: emptyList()) }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center //SpaceBetween
        ) {
//            Box(
//                Modifier.background(
//                    color = MaterialTheme.colorScheme.surfaceBright,
//                    shape = RoundedCornerShape(12.dp)
//                ).size(64.dp), contentAlignment = Alignment.Center
//            ) {
//                Text("つ", fontSize = 48.sp)
//            }

            Button(onClick = onCreateNew) {
                Text("New Reminder")
            }
        }

        val listState = rememberLazyListState()
        val scrollbarAdapter = rememberScrollbarAdapter(listState)

        Box(contentAlignment = Alignment.Center) {
            LazyColumn(
                state = listState,
                // contentPadding = PaddingValues(2.dp),
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp))
            ) {
                items(incomingReminders, key = { it.id }) {
                    ReminderListItem(it, onEdit, onDelete, onRestart)
                }

                if (oldReminders.isNotEmpty()) {
                    item(Unit) {
                        Text("Expired", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    }

                    items(oldReminders, key = { it.id }) {
                        ReminderListItem(it, onEdit, onDelete, onRestart)
                    }
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
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier.fillMaxWidth().height(100.dp).padding(4.dp).then(
            if (reminder.isCompleted) Modifier.border(
                3.dp, MaterialTheme.colorScheme.surfaceContainerLow.copy(0.5f),
                RoundedCornerShape(8.dp)
            ) else Modifier.border(
                3.dp, MaterialTheme.colorScheme.surfaceContainerLow,
                RoundedCornerShape(8.dp)
            )
        ).padding(8.dp)
            .alpha(if (reminder.isCompleted) 0.75f else 1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(reminder.title, maxLines = 1)
            Text(reminder.description, fontSize = 12.sp, maxLines = 1)
            Text(remember(reminder) {
                epochMillisToSimpleDate(run {
                    val remindAt: LocalDateTime = reminder.remindAt
                    val zoneId = ZoneId.systemDefault()
                    val zonedDateTime = remindAt.atZone(zoneId)
                    val offset: ZoneOffset = zonedDateTime.offset
                    remindAt.toEpochSecond(offset) * 1000
                })
            }, fontSize = 12.sp, maxLines = 1)
        }

        Spacer(Modifier.weight(1f))

        TextButton({
            onEdit(reminder)
        }, modifier = Modifier) {
            Text("✏", modifier = Modifier)
        }

        val scale = remember { androidx.compose.animation.core.Animatable(1f) }

        if (reminder.isTimer)
            TextButton({
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
                Text("🔁", modifier = Modifier)
            }

        TextButton({
            onRemove(reminder)
        }, shape = CircleShape) {
            Text("X")
        }
    }
}