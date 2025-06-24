package com.github.catomon.tsukaremi.data.mapper

import com.github.catomon.tsukaremi.data.local.ReminderEntity
import com.github.catomon.tsukaremi.domain.model.Reminder

fun Reminder.toEntity() = ReminderEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    remindAt = this.remindAt,
    remindIn = this.remindIn,
    isTimer = this.isTimer,
    isCompleted = this.isCompleted,
)

fun ReminderEntity.toDomain() = Reminder(
    id = this.id,
    title = this.title,
    description = this.description,
    remindAt = this.remindAt,
    remindIn = this.remindIn,
    isTimer = this.isTimer,
    isCompleted = this.isCompleted,
)