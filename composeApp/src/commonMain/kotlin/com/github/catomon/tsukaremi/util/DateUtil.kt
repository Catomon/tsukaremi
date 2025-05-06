package com.github.catomon.tsukaremi.util

import java.text.DateFormat
import java.text.DateFormat.getTimeInstance
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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

fun combineDateAndTime(
    dateMillis: Long,
    hours: Int,
    minutes: Int,
    timeZone: TimeZone = TimeZone.getDefault()
): Instant {
    val instant = Instant.ofEpochMilli(dateMillis)
    val localDateTime = LocalDateTime.ofInstant(instant, timeZone.toZoneId())
    val combinedLocalDateTime = LocalDateTime.of(
        localDateTime.toLocalDate(), LocalTime.of(hours, minutes)
    )
    val zoneOffset = timeZone.toZoneId().rules.getOffset(combinedLocalDateTime)
    val combinedInstant = combinedLocalDateTime.toInstant(zoneOffset)
    return combinedInstant
}