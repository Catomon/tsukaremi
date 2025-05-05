package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.catomon.tsukaremi.domain.model.Reminder
import kotlinx.serialization.Serializable

@Serializable
object ListDestination

@Composable
fun ListScreen(reminders: List<Reminder>, onCreateNew: () -> Unit, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier.background(
                    color = MaterialTheme.colorScheme.surfaceBright,
                    shape = RoundedCornerShape(12.dp)
                ).size(64.dp), contentAlignment = Alignment.Center
            ) {
                Text("つ", fontSize = 48.sp)
            }

            ElevatedButton(onClick = onCreateNew) {
                Text("New Reminder")
            }
        }

        val listState = rememberLazyListState()
        val scrollbarAdapter = rememberScrollbarAdapter(listState)

        Box(contentAlignment = Alignment.Center) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(2.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(reminders, key = { it.id }) {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth().height(100.dp).padding(2.dp)
                    ) {
                        Text(it.title + it.remindAt)
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