package com.github.catomon.tsukaremi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.catomon.tsukaremi.domain.ReminderManager
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class EditViewModel(
    val repository: RemindersRepository,
    private val reminderManager: ReminderManager,
) : ViewModel() {
    fun saveReminder(reminder: Reminder) {
        viewModelScope.launch {
            if (reminder.id == 0) {
                val reminder = reminder.copy(id = Random.nextInt())
                repository.insertReminder(reminder)
                reminderManager.scheduleReminder(reminder)
            } else {
                repository.updateReminder(reminder)
                reminderManager.scheduleReminder(reminder)
            }
        }
    }
}