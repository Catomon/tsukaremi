package com.github.catomon.tsukaremi.ui.windows

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.viewModelScope
import com.github.catomon.tsukaremi.ui.components.WindowDraggableArea
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalWindow
import com.github.catomon.tsukaremi.ui.modifiers.luckyWindowDecoration
import com.github.catomon.tsukaremi.ui.screens.TsukaremiMainScreen
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.ic_cyclone
import java.awt.Dimension

@Composable
fun ApplicationScope.TsukaremiMainWindow(modifier: Modifier = Modifier) {
    val windowState =
        rememberWindowState(width = WindowConfig.WIDTH.dp, height = WindowConfig.HEIGHT.dp)

    val trayState = rememberTrayState()

    Tray(icon = painterResource(Res.drawable.ic_cyclone), state = trayState)

    Window(
        title = WindowConfig.title,
        state = windowState,
        onCloseRequest = ::exitApplication,
        icon = painterResource(Res.drawable.ic_cyclone),
        undecorated = true,
        resizable = false,
        transparent = WindowConfig.isTransparent,
        alwaysOnTop = false
    ) {
        window.minimumSize = Dimension(WindowConfig.WIDTH / 2, WindowConfig.HEIGHT / 2)

        val viewModel: MainViewModel = koinViewModel()

        LaunchedEffect(Unit) {
            viewModel.viewModelScope.launch {
                viewModel.reminderEvents.collect { reminder ->
                    trayState.sendNotification(
                        Notification(
                            title = "Reminder: ${reminder.title}",
                            message = reminder.description.take(100)
                                .let { if (it.length == 100) it.dropLast(3) + "..." else it }
                        )
                    )
                }
            }
        }

        TsukaremiTheme {
            WindowDraggableArea {
                CompositionLocalProvider(LocalWindow provides window) {
                    TsukaremiMainScreen(
                        viewModel = viewModel,
                        exitApplication = ::exitApplication,
                        modifier = modifier.luckyWindowDecoration().fillMaxSize()
                    )
                }
            }
        }
    }
}