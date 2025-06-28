package com.github.catomon.tsukaremi.domain.repository

import com.github.catomon.tsukaremi.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface RemindersRepository {

    suspend fun insertReminder(reminder: Reminder)

    suspend fun updateReminder(reminder: Reminder)

    suspend fun deleteReminder(reminder: Reminder)

    fun getReminderByIdAsFlow(id: Int): Flow<Reminder?>

    suspend fun getReminderById(id: Int): Reminder?

    fun getAllReminders(): Flow<List<Reminder>>

    suspend fun getAllRemindersList(): List<Reminder>
}