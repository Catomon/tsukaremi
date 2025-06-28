package com.github.catomon.tsukaremi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.catomon.tsukaremi.domain.ReminderService
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class EditViewModel(
    val repository: RemindersRepository,
    private val reminderService: ReminderService,
) : ViewModel() {
    fun saveReminder(reminder: Reminder) {
        viewModelScope.launch {
            if (reminder.id == 0) {
                val reminder = reminder.copy(id = Random.nextInt())
                repository.insertReminder(reminder)
                reminderService.scheduleReminder(reminder)
            } else {
                repository.updateReminder(reminder)
                reminderService.scheduleReminder(reminder)
            }
        }
    }
}