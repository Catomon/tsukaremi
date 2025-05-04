package com.github.catomon.tsukaremi.util

import java.text.DateFormat
import java.text.DateFormat.getTimeInstance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun DateFormat.is24HrFormat(): Boolean {
    val dateFormat: DateFormat = getTimeInstance(DateFormat.SHORT, Locale.getDefault())
    if (dateFormat is SimpleDateFormat) {
        val pattern = dateFormat.toPattern()
        return if (pattern.contains("H")) {
            true
        } else if (pattern.contains("h")) {
            false
        } else {
            true
        }
    }

    return false
}

fun formatMillisToDateString(millis: Long): String {
    val pattern = "dd/MM/yyyy"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    val date = Date(millis)
    return formatter.format(date)
}