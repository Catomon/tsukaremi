package com.github.catomon.tsukaremi.data.local

import kotlinx.serialization.Serializable

@Serializable
enum class ReminderMode {
    WITH_SOUND,
    WITHOUT_SOUND,
    SOUND_ONLY
}

@Serializable
enum class WindowBehavior {
    ALWAYS_ON_TOP,
    NORMAL_WINDOW,
    BORDERLESS_SAFE
}

@Serializable
data class AppSettings(
    val hideReminderAfter: Long = 0,
    val reminderMode: ReminderMode = ReminderMode.WITH_SOUND,
    val windowBehavior: WindowBehavior = WindowBehavior.ALWAYS_ON_TOP,
    val showEffects: Boolean = true
)