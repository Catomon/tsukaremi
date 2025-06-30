package com.github.catomon.tsukaremi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberTrayState
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import com.github.catomon.tsukaremi.ui.windows.ReminderWindow
import com.github.catomon.tsukaremi.ui.windows.TsukaremiMainWindow
import com.github.catomon.tsukaremi.ui.windows.WindowConfig
import com.github.catomon.tsukaremi.util.isNotificationAllowed
import com.github.catomon.tsukaremi.util.logMsg
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.app_icon
import java.awt.Toolkit
import java.time.LocalDateTime
import java.time.ZoneOffset

private val screenSize = Toolkit.getDefaultToolkit().screenSize
private val screenWidth = screenSize.width
private val screenHeight = screenSize.height

object AppViewModelStoreOwner : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()

    fun dispose() {
        viewModelStore.clear()
    }
}

@Composable
fun ApplicationScope.TsukaremiApp() =
    CompositionLocalProvider(LocalViewModelStoreOwner provides AppViewModelStoreOwner) {
        val viewModel: MainViewModel = koinViewModel()

        val trayState = rememberTrayState()

        val shownReminders = remember { mutableStateListOf<Reminder>() }
        var lastReminder by remember { mutableStateOf<Reminder?>(null) }

        RemindersUpdater(viewModel, lastReminder, shownReminders)

        Tray(icon = painterResource(Res.drawable.app_icon), state = trayState)

        TsukaremiMainWindow(viewModel)

        if (shownReminders.isNotEmpty())
            shownReminders.takeLast(3).reversed().forEachIndexed { i, reminder ->
                key(reminder.id) {
                    ReminderWindow(
                        reminder,
                        state = remember(i) {
                            WindowState(
                                size = WindowConfig.reminderWindowSize,
                                position = WindowPosition(
                                    screenWidth.dp - WindowConfig.reminderWindowSize.width - 12.dp,
                                    screenHeight.dp - (WindowConfig.reminderWindowSize.height * (i + 1) + 48.dp)
                                )
                            )
                        },
                        onRestart = {
                            viewModel.viewModelScope.launch {
                                viewModel.reminderManager.restartReminder(reminder)
                                shownReminders -= reminder
                            }
                        },
                        onDismiss = {
                            shownReminders -= reminder
                        }
                    )
                }
            }
    }

@Composable
private fun RemindersUpdater(
    viewModel: MainViewModel,
    lastReminder: Reminder?,
    shownReminders: SnapshotStateList<Reminder>
) {
    //expire old reminders on start
    var lastReminder1 = lastReminder
    LaunchedEffect(Unit) {
        viewModel.viewModelScope.launch {
            val reminders = viewModel.repository.getAllRemindersList()
            val now = LocalDateTime.now(ZoneOffset.UTC)
            val dueReminders = reminders.filter { reminder ->
                !reminder.isCompleted && reminder.remindAt.isBefore(now.minusMinutes(4))
            }
            dueReminders.forEach { reminder ->
                logMsg("Reminder updated: $reminder")
                viewModel.repository.updateReminder(reminder.copy(isCompleted = true))
            }
        }
    }

    //reminders check loop
    LaunchedEffect(Unit) {
        while (isActive) {
            val now = LocalDateTime.now(ZoneOffset.UTC)
            val dueReminders = viewModel.reminders.value.filter { reminder ->
                !reminder.isCompleted && reminder.remindAt.isBefore(now) && reminder.remindAt.isAfter(
                    now.minusMinutes(5)
                )
            }
            dueReminders.forEach { reminder ->
                viewModel.repository.updateReminder(reminder.copy(isCompleted = true))
                logMsg("Reminder updated: $reminder")
                if (isNotificationAllowed()) {
                    logMsg("Showing reminder: $reminder")

                    lastReminder1 = reminder
                    shownReminders += reminder

                    val lastThree = shownReminders.takeLast(3)
                    shownReminders.clear()
                    shownReminders += lastThree

                    //auto hide reminder if hideReminderAfter > 0
                    val lastReminder = lastReminder1
                    if (lastReminder != null && viewModel.appSettings.value.hideReminderAfter > 0) {
                        viewModel.viewModelScope.launch {
                            delay(viewModel.appSettings.value.hideReminderAfter)
                            shownReminders -= lastReminder
                        }
                    }
                }
            }
            delay(15_000)
        }
    }
}
