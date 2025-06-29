package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.components.DatePickerDialog
import com.github.catomon.tsukaremi.ui.components.TimePickerDialog
import com.github.catomon.tsukaremi.ui.viewmodel.EditViewModel
import com.github.catomon.tsukaremi.util.combineDateAndTime
import com.github.catomon.tsukaremi.util.formatMillisToDateString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.lucky_background_stars
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

typealias HoursMinutes = Pair<Int, Int>

@Serializable
data class EditDestination(val reminderId: Int? = null)

private val defaultTimerRemindInTime = 0 to 0

fun HoursMinutes.decrementTime(
    decrement: Int,
//    is24Hour: Boolean = DateFormat.getTimeInstance().is24HrFormat()
): HoursMinutes {
    var hrs = first
    var mns = second - decrement
//    val maxHour = if (is24Hour) 23 else 11

    while (mns < 0) {
        mns += 60
        hrs -= 1
        if (hrs < 0) {
//            hrs = maxHour
            hrs = 0
            mns = 0
        }
    }
    return HoursMinutes(hrs, mns % 60)
}

fun HoursMinutes.incrementTime(
    increment: Int,
//    is24Hour: Boolean = DateFormat.getTimeInstance().is24HrFormat()
): HoursMinutes {
    val mns = second + increment
    var hrs = first + mns / 60
//    if (hrs >= if (is24Hour) 24 else 12) hrs = 0
    return HoursMinutes(hrs, mns % 60)
}

@Composable
fun EditScreen(
    reminderId: Int? = null,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = koinViewModel(),
) {
    var isLoading by remember { mutableStateOf(false) }

    var reminder by remember { mutableStateOf<Reminder?>(null) }
    var title by remember(reminder) { mutableStateOf(reminder?.title ?: "") }
    var description by remember(reminder) { mutableStateOf(reminder?.description ?: "") }

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
            } ?: (defaultTimerRemindInTime) else reminder?.remindAt?.let { it.hour to it.minute } ?: LocalTime.now()
                .let { it.hour to it.minute })
    }

    val timeSelectableFrom = remember(selectedTime, selectedDateMillis, reminder) {
        getTimeSelectableFromDate(selectedDateMillis)
    }

    LaunchedEffect(Unit) {
        reminderId?.let { reminderId ->
            viewModel.repository.getReminderByIdAsFlow(reminderId).collectLatest { latest ->
                reminder = latest
            }
        }

        isLoading = false
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painterResource(Res.drawable.lucky_background_stars),
            null,
            modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color(0xff9775d5))
        )

        Column(Modifier.background(MaterialTheme.colorScheme.background.copy(0.75f))) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                enabled = !isLoading,
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
                enabled = !isLoading,
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
                    enabled = !isLoading,
                )

                Spacer(Modifier.height(1.dp).weight(0.2f))

                Text("Timer")
                RadioButton(
                    isTimer,
                    onClick = {
                        isTimer = true
                    },
                    enabled = !isLoading,
                )

                Spacer(Modifier.height(1.dp).weight(0.2f))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth().height(150.dp)
            ) {
                if (!isTimer)
                    Button(
                        onClick = {
                            showDatePickDialog = true
                        }, enabled = !isLoading, contentPadding = PaddingValues(32.dp)
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

                if (isTimer)
                    Column {
                        Button({
                            selectedTime = selectedTime.decrementTime(1)
                        }, enabled = !isLoading) {
                            Text("-1")
                        }

                        Button({
                            selectedTime = selectedTime.decrementTime(5)
                        }, enabled = !isLoading) {
                            Text("-5")
                        }

                        Button({
                            selectedTime = selectedTime.decrementTime(30)
                        }, enabled = !isLoading) {
                            Text("-30")
                        }
                    }

                Button(
                    onClick = {
                        showTimePickDialog = true
                    }, enabled = !isLoading, contentPadding = PaddingValues(32.dp)
                ) {
                    Text(
                        remember(isTimer, selectedTime) {
                            if (isTimer) "Remind in:\n" + "%02d:%02d:00".format(
                                selectedTime.first, selectedTime.second
                            ) else "Time:\n" + "%02d:%02d".format(
                                selectedTime.first, selectedTime.second
                            )
                        },
                        modifier = Modifier.sizeIn(minWidth = 96.dp),
                        textAlign = TextAlign.Center
                    )
                }

                if (isTimer)
                    Column {
                        Button({
                            selectedTime = selectedTime.incrementTime(1)
                        }, enabled = !isLoading) {
                            Text("+1")
                        }

                        Button({
                            selectedTime = selectedTime.incrementTime(5)
                        }, enabled = !isLoading) {
                            Text("+5")
                        }

                        Button({
                            selectedTime = selectedTime.incrementTime(30)
                        }, enabled = !isLoading) {
                            Text("+30")
                        }
                    }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                                ZoneOffset.UTC
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
                    enabled = !isLoading,
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
                time = remember(isTimer) {
                    if (isTimer) defaultTimerRemindInTime else LocalTime.now().let { it.hour to it.second }
                },
                timeSelectableFrom = if (isTimer) defaultTimerRemindInTime else timeSelectableFrom
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
