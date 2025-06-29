package com.github.catomon.tsukaremi.domain.model

import com.github.catomon.tsukaremi.util.serializers.LocalDateTimeSerializer
import com.github.catomon.tsukaremi.util.serializers.LocalTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime

@Serializable
data class Reminder(
    val id: Int = 0,
    val title: String,
    val description: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val remindAt: LocalDateTime,
    val remindIn: Long = 0,
    val isTimer: Boolean = false,
    val isCompleted: Boolean = false,
)
