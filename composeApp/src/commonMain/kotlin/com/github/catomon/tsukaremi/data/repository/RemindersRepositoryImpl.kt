package com.github.catomon.tsukaremi.data.repository

import com.github.catomon.tsukaremi.data.local.ReminderDao
import com.github.catomon.tsukaremi.data.mapper.toDomain
import com.github.catomon.tsukaremi.data.mapper.toEntity
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemindersRepositoryImpl(private val reminderDao: ReminderDao) : RemindersRepository {

    override suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insert(reminder.toEntity())
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.insert(reminder.toEntity())
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(reminder.toEntity())
    }

    override fun getReminderByIdAsFlow(id: Int): Flow<Reminder?> {
        return reminderDao.getReminderByIdAsFlow(id).map { it?.toDomain() }
    }

    override suspend fun getReminderById(id: Int): Reminder? {
        return reminderDao.getReminderById(id)?.toDomain()
    }

    override fun getAllReminders(): Flow<List<Reminder>> {
        return reminderDao.getAllReminders().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getAllRemindersList(): List<Reminder> {
        return reminderDao.getAllRemindersList().map { it.toDomain() }
    }
}