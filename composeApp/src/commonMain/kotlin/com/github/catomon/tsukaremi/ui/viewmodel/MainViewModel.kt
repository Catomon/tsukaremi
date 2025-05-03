package com.github.catomon.tsukaremi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import com.github.catomon.tsukaremi.ui.navigation.NavTarget
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    val repository: RemindersRepository,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _navigation = MutableSharedFlow<NavTarget>(extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()

    val reminders = repository.getAllReminders().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onNavigate(destination: String) {
        _navigation.tryEmit(NavTarget.NavigateTo(destination))
    }

    fun onPopBack() {
        _navigation.tryEmit(NavTarget.PopBack)
    }
}
