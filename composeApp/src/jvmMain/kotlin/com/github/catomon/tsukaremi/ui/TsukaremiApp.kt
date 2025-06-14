package com.github.catomon.tsukaremi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import com.github.catomon.tsukaremi.ui.windows.ReminderWindow
import com.github.catomon.tsukaremi.ui.windows.TsukaremiMainWindow
import com.github.catomon.tsukaremi.ui.windows.WindowConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.ic_cyclone
import java.awt.Toolkit

private val screenSize = Toolkit.getDefaultToolkit().screenSize
private val screenWidth = screenSize.width
private val screenHeight = screenSize.height

@Composable
fun ApplicationScope.TsukaremiApp() =
    CompositionLocalProvider(LocalViewModelStoreOwner provides AppViewModelStoreOwner) {
        val viewModel: MainViewModel = koinViewModel()

        val trayState = rememberTrayState()

        val shownReminders = remember { mutableStateListOf<Reminder>() }
        var lastReminder by remember { mutableStateOf<Reminder?>(null) }

        LaunchedEffect(Unit) {
            viewModel.viewModelScope.launch {
                viewModel.reminderEvents.collect { reminder ->
//                trayState.sendNotification(
//                    Notification(
//                        title = "Reminder: ${reminder.title}",
//                        message = reminder.description.take(100)
//                            .let { if (it.length == 100) it.dropLast(3) + "..." else it }
//                    )
//                )

                    lastReminder = reminder
                    shownReminders += reminder
                }
            }
        }

        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(lastReminder) {
            val lastReminder = lastReminder
            if (lastReminder != null && viewModel.appSettings.value.hideReminderAfter > 0) {
                coroutineScope.launch {
                    delay(viewModel.appSettings.value.hideReminderAfter)
                    shownReminders -= lastReminder
                }
            }
        }

        Tray(icon = painterResource(Res.drawable.ic_cyclone), state = trayState)

        TsukaremiMainWindow()

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
                        onDismiss = {
                            shownReminders -= reminder
                        }
                    )
                }
            }
    }

object AppViewModelStoreOwner : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()

    fun dispose() {
        viewModelStore.clear()
    }
}
