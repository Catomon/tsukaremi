package com.github.catomon.tsukaremi.domain

import com.github.catomon.tsukaremi.domain.model.Reminder

interface ReminderService {
    suspend fun scheduleReminder(reminder: Reminder)
    suspend fun cancelReminder(reminder: Reminder)
    suspend fun showNotification(reminder: Reminder)
    suspend fun restartReminder(reminder: Reminder)
}