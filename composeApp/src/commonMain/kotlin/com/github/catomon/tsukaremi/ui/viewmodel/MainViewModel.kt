package com.github.catomon.tsukaremi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.catomon.tsukaremi.data.local.AppSettings
import com.github.catomon.tsukaremi.data.local.platform.userFolderPath
import com.github.catomon.tsukaremi.domain.ReminderService
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import com.github.catomon.tsukaremi.util.logMsg
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.io.files.Path
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class MainViewModel(
    val repository: RemindersRepository,
    val reminderService: ReminderService,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val reminders = repository.getAllReminders().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val settingsStore: KStore<AppSettings> =
        storeOf(file = Path("$userFolderPath/settings.json"))

    private val _appSettings = MutableStateFlow(
        runBlocking {
            loadSettings()
        }
    )
    val appSettings = _appSettings.asStateFlow()

    fun updateSettings(settings: AppSettings) {
        _appSettings.value = settings
    }

    suspend fun saveSettings(settings: AppSettings = appSettings.value) {
        settingsStore.set(settings)
    }

    suspend fun loadSettings() = run {
        settingsStore.get() ?: settingsStore.set(AppSettings())
        settingsStore.get() ?: error("Could not set appSettings KStore.")
    }
}
