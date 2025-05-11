package com.github.catomon.tsukaremi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.WindowPosition
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

@Composable
fun ApplicationScope.TsukaremiApp() =
    CompositionLocalProvider(LocalViewModelStoreOwner provides AppViewModelStoreOwner) {
        val viewModel: MainViewModel = koinViewModel()

        val trayState = rememberTrayState()

        var shownReminder by remember { mutableStateOf<Reminder?>(null) }

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

                    shownReminder = reminder
                }
            }
        }

        LaunchedEffect(shownReminder) {
            if (shownReminder != null) {
                delay(10_000)
                shownReminder = null
            }
        }

        Tray(icon = painterResource(Res.drawable.ic_cyclone), state = trayState)

        TsukaremiMainWindow()

        if (shownReminder != null)
            ReminderWindow(
                shownReminder!!,
                state = rememberWindowState(
                    size = WindowConfig.reminderWindowSize,
                    position = WindowPosition(Alignment.BottomEnd)
                ),
                onDismiss = {
                    shownReminder = null
                }
            )
    }

object AppViewModelStoreOwner : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()

    fun dispose() {
        viewModelStore.clear()
    }
}
