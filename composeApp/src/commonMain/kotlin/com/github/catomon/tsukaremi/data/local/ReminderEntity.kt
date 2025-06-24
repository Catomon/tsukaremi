package com.github.catomon.tsukaremi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(true)
    val id: Int,
    val title: String,
    val description: String,
    val remindAt: LocalDateTime,
    val remindIn: Long,
    val isTimer: Boolean,
    val isCompleted: Boolean,
)
