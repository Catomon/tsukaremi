package com.github.catomon.tsukaremi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import com.github.catomon.tsukaremi.util.logMsg
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainViewModel(
    val repository: RemindersRepository,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val reminders = repository.getAllReminders().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _reminderEvents = MutableSharedFlow<Reminder>(extraBufferCapacity = 10)
    val reminderEvents = _reminderEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            val reminders = repository.getAllRemindersList()
            val now = LocalDateTime.now()
            val dueReminders = reminders.filter { reminder ->
                !reminder.isCompleted && reminder.remindAt.isBefore(now.minusMinutes(4))
            }
            dueReminders.forEach { reminder ->
                this@MainViewModel.logMsg("Reminder updated: $reminder")
                repository.updateReminder(reminder.copy(isCompleted = true))
            }
        }

        startReminderChecker()
    }

    private fun startReminderChecker() {
        viewModelScope.launch {
            while (isActive) {
                val now = LocalDateTime.now()
                val dueReminders = reminders.value.filter { reminder ->
                    !reminder.isCompleted && reminder.remindAt.isBefore(now) && reminder.remindAt.isAfter(
                        now.minusMinutes(5)
                    )
                }
                dueReminders.forEach { reminder ->
                    onReminder(reminder)
                }
                delay(15_000)
            }
        }
    }

    private fun onReminder(reminder: Reminder) {
        this@MainViewModel.logMsg("Showing reminder: $reminder")
        viewModelScope.launch {
            repository.updateReminder(reminder.copy(isCompleted = true))
            this@MainViewModel.logMsg("Reminder updated: $reminder")
            _reminderEvents.tryEmit(reminder)
        }
    }
}
