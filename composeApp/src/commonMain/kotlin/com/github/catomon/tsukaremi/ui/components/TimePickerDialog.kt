package com.github.catomon.tsukaremi.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.catomon.tsukaremi.util.is24HrFormat
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onTimeSelected: (Pair<Int, Int>) -> Unit,
    onDismiss: () -> Unit,
    time: Pair<Int, Int> = remember { LocalTime.now().let { it.hour to it.second } },
    timeSelectableFrom: Pair<Int, Int>? = null
) {
    val timePickerState = rememberTimePickerState(
        time.first,
        time.second,
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

    androidx.compose.material3.DatePickerDialog(
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
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(top = 32.dp)) {
            TimePicker(
                state = timePickerState
            )
        }
    }
}