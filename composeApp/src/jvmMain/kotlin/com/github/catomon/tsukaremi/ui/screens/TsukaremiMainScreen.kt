package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.catomon.tsukaremi.domain.model.Reminder
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalNavController
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalWindow
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import com.github.catomon.tsukaremi.ui.navigation.NavTarget
import kotlinx.coroutines.delay
import org.koin.java.KoinJavaComponent.get
import java.time.LocalDateTime

@Composable
fun TsukaremiMainScreen(
    viewModel: MainViewModel = get(MainViewModel::class.java), modifier: Modifier = Modifier
) = Surface(modifier = modifier) {
    val window = LocalWindow.current
    val navController = rememberNavController()
    val reminders by viewModel.reminders.collectAsState()

    LaunchedEffect(navController, viewModel.navigation) {
        viewModel.navigation.collect { navTarget ->
            when (navTarget) {
                is NavTarget.NavigateTo -> navController.navigate(navTarget.route)
                is NavTarget.PopBack -> navController.popBackStack()
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("つかれみ", Modifier.clip(RoundedCornerShape(8.dp)).clickable {
                    viewModel.onNavigate(ListDestination.toString())
                })

                Text("---", modifier = Modifier.clickable {
                    window.isMinimized = true
                })
            } //つかれみ //ゆきのて //ツカレミ

            CompositionLocalProvider(LocalNavController provides navController) {
                NavHost(
                    navController = navController,
                    startDestination = ListDestination.toString(),
                    enterTransition = { slideInHorizontally { it } },
                    exitTransition = { slideOutHorizontally { it } }
                ) {
                    composable(ListDestination.toString()) {
                        ListScreen(reminders = reminders, onCreateNew = {
                            viewModel.onNavigate(EditDestination.toString())
                        })
                    }

                    composable(EditDestination.toString()) {
                        EditScreen(onBack = viewModel::onPopBack, onConfirm = viewModel::onPopBack)
                    }

                    composable(SettingsDestination.toString()) {
                        SettingsScreen(
                            onBack = viewModel::onPopBack, onExitApp = viewModel::onPopBack
                        )
                    }
                }
            }
        }
    }
}