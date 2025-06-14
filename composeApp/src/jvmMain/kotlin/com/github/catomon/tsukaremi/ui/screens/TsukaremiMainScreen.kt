package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.catomon.tsukaremi.ui.components.LuckySurface
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalNavController
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalWindow
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import kotlin.system.exitProcess

@Composable
fun TsukaremiMainScreen(
    viewModel: MainViewModel = koinViewModel(),
    exitApplication: () -> Unit = { exitProcess(0) },
    modifier: Modifier = Modifier
) = Surface(modifier = modifier) {
    val window = LocalWindow.current
    val navController = rememberNavController()
    val reminders by viewModel.reminders.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background.copy(0.9f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp).height(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Tsukaremi",
                fontSize = 16.sp,
                modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable {
                    navController.navigate(ListDestination)
                })

            Text("---", modifier = Modifier.clickable {
                window.isMinimized = true
            })
        } //つかれみ //ゆきのて //ツカレミ

        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(
                navController = navController,
                startDestination = ListDestination,
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { it } }
            ) {
                composable<ListDestination> {
                    ListScreen(
                        reminders = reminders, onCreateNew = {
                            navController.navigate(EditDestination())
                        },
                        onDelete = {
                            viewModel.viewModelScope.launch {
                                viewModel.repository.deleteReminder(it)
                            }
                        }
                    )
                }

                composable<EditDestination> {
                    EditScreen(
                        onBack = navController::navigateUp,
                        onConfirm = navController::navigateUp
                    )
                }

                composable<SettingsDestination> {
                    SettingsScreen(
                        onBack = navController::navigateUp,
                        onExitApp = exitApplication
                    )
                }
            }
        }
    }
}