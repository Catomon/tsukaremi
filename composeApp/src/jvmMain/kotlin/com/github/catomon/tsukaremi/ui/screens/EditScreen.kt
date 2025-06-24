package com.github.catomon.tsukaremi.ui.screens

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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.components.DatePickerDialog
import com.github.catomon.tsukaremi.ui.components.TimePickerDialog
import com.github.catomon.tsukaremi.ui.viewmodel.EditViewModel
import com.github.catomon.tsukaremi.util.combineDateAndTime
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
    reminderId: Int? = null,
    onBack: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = koinViewModel(),
) {
    val reminder by viewModel.reminder
    var title by remember(reminder) { mutableStateOf(reminder?.title ?: "") }
    var description by remember(reminder) { mutableStateOf(reminder?.title ?: "") }

    var isTimer by remember(reminder) { mutableStateOf(reminder?.isTimer == true) }

    var showDatePickDialog by remember { mutableStateOf(false) }
    var showTimePickDialog by remember { mutableStateOf(false) }

    var selectedDateMillis by remember(reminder) {
        mutableStateOf(
            reminder?.remindAt?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
                ?: System.currentTimeMillis()
        )
    }
    var selectedTime by remember(isTimer, reminder) {
        mutableStateOf(
            if (isTimer) reminder?.remindIn?.let {
                val mnsTotal = it / 1000 / 60
                val hrs = mnsTotal / 60
                val mns = mnsTotal - hrs * 60
                hrs.toInt() to mns.toInt()
            } ?: (0 to 0) else reminder?.remindAt?.let { it.hour to it.minute } ?: LocalTime.now()
                .let { it.hour to it.minute })
    }

    val timeSelectableFrom = remember(selectedTime, selectedDateMillis, reminder) {
        getTimeSelectableFromDate(selectedDateMillis)
    }

    val loading by viewModel.loading

    LaunchedEffect(Unit) {
        viewModel.loadReminder(reminderId ?: return@LaunchedEffect)
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                enabled = !loading,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
//                    unfocusedLabelColor = YukiTheme.colors.surface,
//                    focusedLabelColor = YukiTheme.colors.surface,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent
                ),
                maxLines = 1
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().weight(1f).padding(4.dp),
                enabled = !loading,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
//                    unfocusedLabelColor = YukiTheme.colors.surface,
//                    focusedLabelColor = YukiTheme.colors.surface,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent
                )
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.height(1.dp).weight(0.2f))

                Text("Alarm")
                RadioButton(
                    !isTimer,
                    onClick = {
                        isTimer = false
                    },
                    enabled = !loading,
                )

                Spacer(Modifier.height(1.dp).weight(0.2f))

                Text("Timer")
                RadioButton(
                    isTimer,
                    onClick = {
                        isTimer = true
                    },
                    enabled = !loading,
                )

                Spacer(Modifier.height(1.dp).weight(0.2f))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (!isTimer)
                    Button(
                        onClick = {
                            showDatePickDialog = true
                        }, enabled = !loading, contentPadding = PaddingValues(32.dp)
                    ) {
                        Text(
                            remember(selectedDateMillis) {
                                "Date:\n" + formatMillisToDateString(
                                    selectedDateMillis
                                )
                            },
                            modifier = Modifier.sizeIn(minWidth = 96.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                Button(
                    onClick = {
                        showTimePickDialog = true
                    }, enabled = !loading, contentPadding = PaddingValues(32.dp)
                ) {
                    Text(
                        remember(isTimer, selectedTime) {
                            "Time:\n" + "%02d:%02d".format(
                                selectedTime.first, selectedTime.second
                            )
//
//                            if (isTimer) "Time:\n00:00" else "Time:\n" + "%02d:%02d".format(
//                                selectedTime.first, selectedTime.second
//                            )
                        },
                        modifier = Modifier.sizeIn(minWidth = 96.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onBack, modifier = Modifier.weight(0.40f)) {
                    Text("BACK")
                }

                TextButton(
                    onClick = {
                        val remindIn = selectedTime.first * 60L * 60L * 1000L + selectedTime.second * 60L * 1000L
                        val remindAt = if (isTimer) {
                            val instant = Instant.ofEpochMilli(System.currentTimeMillis() + remindIn)
                            val zoneId = ZoneId.systemDefault()
                            instant.atZone(zoneId).toLocalDateTime()
                        } else {
                            LocalDateTime.ofInstant(
                                combineDateAndTime(
                                    selectedDateMillis,
                                    selectedTime.first,
                                    selectedTime.second
                                ),
                                ZoneId.systemDefault()
                            )
                        }

                        val updatedReminder = Reminder(
                            id = reminder?.id ?: 0,
                            title = title,
                            description = description,
                            remindAt = remindAt,
                            remindIn = remindIn,
                            isCompleted = false,
                            isTimer = isTimer
                        )
                        viewModel.saveReminder(updatedReminder)
                        onConfirm()
                    },
                    enabled = !loading,
                    modifier = Modifier.weight(0.40f)
                ) {
                    Text("CONFIRM")
                }
            }
        }

        if (showDatePickDialog) {
            DatePickerDialog(onDateSelected = {
                selectedDateMillis = it
            }, onDismiss = {
                showDatePickDialog = false
            })
        } else {
            if (showTimePickDialog) TimePickerDialog(
                onTimeSelected = {
                    selectedTime = it
                }, onDismiss = {
                    showTimePickDialog = false
                },
                time = remember(isTimer) { if (isTimer) 0 to 0 else LocalTime.now().let { it.hour to it.second } },
                timeSelectableFrom = if (isTimer) 0 to 0 else timeSelectableFrom
            )
        }
    }
}

private fun getTimeSelectableFromDate(selectedDateTime: Long): Pair<Int, Int> {
    val selectedDate =
        Instant.ofEpochMilli(selectedDateTime).atZone(ZoneId.systemDefault()).toLocalDate()

    val currentDate = LocalDate.now()

    return if (selectedDate == currentDate) {
        val currentTime = LocalTime.now()
        currentTime.hour to currentTime.minute
    } else {
        0 to 0
    }
}
