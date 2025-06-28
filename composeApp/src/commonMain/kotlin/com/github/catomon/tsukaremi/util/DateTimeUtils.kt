package com.github.catomon.tsukaremi.util

import java.time.Instant
import java.time.LocalDate
import java.util.TimeZone

fun epochMillisToSimpleDate(epochMillis: Long): String {
    val instant = Instant.ofEpochMilli(epochMillis)
    val userTimeZone = TimeZone.getDefault()
    val localDateTime = instant.atZone(userTimeZone.toZoneId())
    val year = localDateTime.year
    return "${if (year != LocalDate.now().year) "$year." else ""}${
        localDateTime.monthValue.toString().padStart(2, '0')
    }.${localDateTime.dayOfMonth.toString().padStart(2, '0')}" + " " +
            "${
                localDateTime.hour.toString().padStart(2, '0')
            }:${localDateTime.minute.toString().padStart(2, '0')}"
}