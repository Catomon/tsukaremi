package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.catomon.tsukaremi.domain.model.Reminder
import kotlinx.serialization.Serializable

@Serializable
object ListDestination

@Composable
fun ListScreen(
    reminders: List<Reminder>,
    onCreateNew: () -> Unit,
    onDelete: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    val (oldReminders, incomingReminders) = remember(reminders) {
        reminders.groupBy { it.isCompleted }
            .let { (it[true]?.reversed() ?: emptyList()) to (it[false] ?: emptyList()) }
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

            ElevatedButton(onClick = onCreateNew) {
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
                    ReminderListItem(it, onDelete)
                }

                if (oldReminders.isNotEmpty()) {
                    item(Unit) {
                        Text("- Old -", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    }

                    items(oldReminders, key = { it.id }) {
                        ReminderListItem(it, onDelete)
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
    onRemove: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth().height(100.dp).padding(4.dp)
            .alpha(if (reminder.isCompleted) 0.75f else 1f)
    ) {
        Text(reminder.title)
        Text(reminder.description)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(reminder.remindAt.toString())
            TextButton({
                onRemove(reminder)
            }, shape = CircleShape) {
                Text("X")
            }
        }
    }
}