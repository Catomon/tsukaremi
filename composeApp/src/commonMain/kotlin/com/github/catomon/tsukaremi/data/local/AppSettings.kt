package com.github.catomon.tsukaremi.data.local

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val hideReminderAfter: Long = 0
)