package com.github.catomon.tsukaremi.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.catomon.tsukaremi.util.is24HrFormat
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.time.LocalTime
import java.time.Year
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class)
private object FromNowSelectableDates : SelectableDates {
    @OptIn(ExperimentalTime::class)
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= Clock.System.now().toEpochMilliseconds()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= Year.now().value
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val currentTimeMillis = System.currentTimeMillis()
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = currentTimeMillis,
        initialDisplayedMonthMillis = currentTimeMillis,
        selectableDates = FromNowSelectableDates
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    onDateSelected(millis)
                }
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onTimeSelected: (Pair<Int, Int>) -> Unit,
    onDismiss: () -> Unit,
    timeSelectableFrom: Pair<Int, Int>? = null
) {
    val currentDayTime = LocalTime.now()
    val timePickerState = rememberTimePickerState(
        currentDayTime.hour,
        currentDayTime.minute,
        DateFormat.getTimeInstance().is24HrFormat()
    )

    fun isInValidTimeRange() =
        timeSelectableFrom == null || (timeSelectableFrom.first * 60 + timeSelectableFrom.second < timePickerState.hour * 60 + timePickerState.minute)

    var okEnabled by remember(timeSelectableFrom) {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        while (true) {
            okEnabled = isInValidTimeRange()
            delay(200)
        }
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                okEnabled = isInValidTimeRange()
                if (okEnabled) {
                    onTimeSelected(timePickerState.hour to timePickerState.minute)
                    onDismiss()
                }
            }, enabled = okEnabled) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    ) {
        TimePicker(state = timePickerState)
    }
}