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
import com.github.catomon.tsukaremi.ui.components.DatePickerDialog
import com.github.catomon.tsukaremi.ui.components.TimePickerDialog
import com.github.catomon.tsukaremi.util.formatMillisToDateString
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@Serializable
object EditDestination {
    override fun toString(): String {
        return "edit"
    }
}

@Composable
fun EditScreen(
    onBack: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {

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
                        .weight(0.99f)
                )

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.99f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        showDatePickDialog = true
                    }) {
                        Text(remember(selectedDateTime) { formatMillisToDateString(selectedDateTime) })
                    }

                    Button(onClick = {
                        showTimePickDialog = true
                    }) {
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

                    TextButton(onClick = onConfirm) {
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
