package com.github.catomon.tsukaremi.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EditViewModel(
    private val repository: RemindersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val reminderId: Int? = savedStateHandle["reminderId"]

    var reminder = mutableStateOf<Reminder?>(null)

    var loading = mutableStateOf(true)

    init {
        viewModelScope.launch {
            reminderId?.let { reminderId ->
                repository.getReminderById(reminderId).collectLatest { latest ->
                    reminder.value = latest
                }
            }

            loading.value = false
        }
    }

    fun saveReminder(reminderToSave: Reminder) {
        viewModelScope.launch {
            if (reminderToSave.id == 0) {
                repository.insertReminder(reminderToSave)
            } else {
                repository.updateReminder(reminderToSave)
            }
        }
    }
}