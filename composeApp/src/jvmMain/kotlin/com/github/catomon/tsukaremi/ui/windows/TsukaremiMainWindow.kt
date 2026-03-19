package com.github.catomon.tsukaremi.ui.windows

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.github.catomon.tsukaremi.ui.components.WindowDraggableArea
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalWindow
import com.github.catomon.tsukaremi.ui.modifiers.luckyWindowDecoration
import com.github.catomon.tsukaremi.ui.screens.TsukaremiMainScreen
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import org.jetbrains.compose.resources.painterResource
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.app_icon
import java.awt.Dimension

@Composable
fun ApplicationScope.TsukaremiMainWindow(
    viewModel: MainViewModel,
    onCloseRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val windowState =
        rememberWindowState(
            width = WindowConfig.WIDTH.dp, height = WindowConfig.HEIGHT.dp, position = WindowPosition.Aligned(
                Alignment.Center
            )
        )

    Window(
        title = WindowConfig.title,
        state = windowState,
        onCloseRequest = onCloseRequest,
        icon = painterResource(Res.drawable.app_icon),
        undecorated = true,
        resizable = true,
        transparent = WindowConfig.isTransparent,
        alwaysOnTop = false
    ) {
        window.minimumSize = Dimension(WindowConfig.WIDTH / 2, WindowConfig.HEIGHT / 2)
        window.maximumSize = Dimension(WindowConfig.WIDTH, WindowConfig.HEIGHT)

        TsukaremiTheme {
            WindowDraggableArea {
                CompositionLocalProvider(LocalWindow provides window) {
                    TsukaremiMainScreen(
                        viewModel = viewModel,
                        onCloseRequest = onCloseRequest,
                        modifier = modifier.luckyWindowDecoration().fillMaxSize()
                    )
                }
            }
        }
    }
}