package com.github.catomon.tsukaremi.data

import com.github.catomon.tsukaremi.domain.ReminderService
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import java.time.Instant
import java.time.ZoneId

actual class ReminderServiceImpl(private val repository: RemindersRepository) : ReminderService {
    override suspend fun scheduleReminder(reminder: Reminder) {
        if (reminder.isCompleted) {
            repository.updateReminder(reminder.copy(isCompleted = false))
        }
    }

    override suspend fun cancelReminder(reminder: Reminder) {
        if (!reminder.isCompleted) {
            repository.updateReminder(reminder.copy(isCompleted = true))
        }
    }

    override suspend fun showNotification(reminder: Reminder) {
        //todo desktop native notification
    }

    override suspend fun restartReminder(reminder: Reminder) {
        repository.updateReminder(
            reminder.copy(
                isCompleted = false,
                remindAt = run {
                    val instant = Instant.ofEpochMilli(System.currentTimeMillis() + reminder.remindIn)
                    val zoneId = ZoneId.systemDefault()
                    instant.atZone(zoneId).toLocalDateTime()
                }
            ))
    }
}