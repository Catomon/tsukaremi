package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.components.DatePickerDialog
import com.github.catomon.tsukaremi.ui.components.TimePickerDialog
import com.github.catomon.tsukaremi.ui.viewmodel.EditViewModel
import com.github.catomon.tsukaremi.util.formatMillisToDateString
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@Serializable
data class EditDestination(val reminderId: Int? = null)

@Composable
fun EditScreen(
    viewModel: EditViewModel = koinViewModel(),
    onBack: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val reminder by viewModel.reminder
    val loading by viewModel.loading
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showDatePickDialog by remember { mutableStateOf(false) }
    var showTimePickDialog by remember { mutableStateOf(false) }
    var selectedDateTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now().let { it.hour to it.minute }) }
    val timeSelectableFrom = remember(selectedDateTime, selectedTime) {
        getTimeSelectableFromDate(selectedDateTime)
    }

    Surface(modifier) {
        Box(modifier = modifier.fillMaxSize()) {
            Column {
                Text("Edit")
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.99f),
                    enabled = !loading
                )

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.99f),
                    enabled = !loading
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            showDatePickDialog = true
                        },
                        enabled = !loading
                    ) {
                        Text(remember(selectedDateTime) { formatMillisToDateString(selectedDateTime) })
                    }

                    Button(
                        onClick = {
                            showTimePickDialog = true
                        },
                        enabled = !loading
                    ) {
                        Text("%02d:%02d".format(selectedTime.first, selectedTime.second))
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onBack) {
                        Text("BACK")
                    }

                    TextButton(
                        onClick = {
                            val updatedReminder = Reminder(
                                id = reminder?.id ?: 0,
                                title = title,
                                description = description,
                                remindAt = LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli(selectedDateTime + selectedTime.first.toLong() * 60L * 60L * 1000L),
                                    ZoneId.systemDefault()
                                ),
                                isCompleted = false,
                                repeatDailyFrom = null,
                                repeatDailyTo = null
                            )
                            viewModel.saveReminder(updatedReminder)
                            onConfirm()
                        },
                        enabled = !loading
                    ) {
                        Text("CONFIRM")
                    }
                }
            }

            if (showDatePickDialog) {
                DatePickerDialog(
                    onDateSelected = {
                        selectedDateTime = it
                    },
                    onDismiss = {
                        showDatePickDialog = false
                    }
                )
            } else {
                if (showTimePickDialog)
                    TimePickerDialog(
                        onTimeSelected = {
                            selectedTime = it
                        },
                        onDismiss = {
                            showTimePickDialog = false
                        },
                        timeSelectableFrom = timeSelectableFrom
                    )
            }
        }
    }
}

private fun getTimeSelectableFromDate(selectedDateTime: Long): Pair<Int, Int> {
    val selectedDate = Instant
        .ofEpochMilli(selectedDateTime)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val currentDate = LocalDate.now()

    return if (selectedDate == currentDate) {
        val currentTime = LocalTime.now()
        currentTime.hour to currentTime.minute
    } else {
        0 to 0
    }
}
