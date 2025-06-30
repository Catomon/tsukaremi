package com.github.catomon.tsukaremi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.catomon.tsukaremi.data.local.AppSettings
import com.github.catomon.tsukaremi.data.local.platform.userFolderPath
import com.github.catomon.tsukaremi.domain.ReminderManager
import com.github.catomon.tsukaremi.domain.repository.RemindersRepository
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.io.files.Path

class MainViewModel(
    val repository: RemindersRepository,
    val reminderManager: ReminderManager,
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

    fun saveSettings(settings: AppSettings = appSettings.value) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsStore.set(settings)
        }
    }

    suspend fun loadSettings() = run {
        withContext(Dispatchers.IO) {
            settingsStore.get() ?: settingsStore.set(AppSettings())
            settingsStore.get() ?: error("Could not set appSettings KStore.")
        }
    }
}
