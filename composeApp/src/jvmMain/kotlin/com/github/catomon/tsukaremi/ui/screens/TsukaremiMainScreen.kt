package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalNavController
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalWindow
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.close_window
import tsukaremi.composeapp.generated.resources.minimize_window
import tsukaremi.composeapp.generated.resources.top_bar_background
import kotlin.system.exitProcess

@Composable
fun TsukaremiMainScreen(
    viewModel: MainViewModel = koinViewModel(),
    exitApplication: () -> Unit = { exitProcess(0) },
    modifier: Modifier = Modifier
) = Surface(modifier = modifier) {
    val window = LocalWindow.current
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        remember(currentBackStackEntry) { currentBackStackEntry?.destination?.route?.split("?")?.firstOrNull() }

    val reminders by viewModel.reminders.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(horizontal = 6.dp)
                .height(120.dp),
        ) {
            Image(
                painterResource(Res.drawable.top_bar_background),
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize().clip(
                    RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
                )
            )

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.TopEnd)) {
                Text(
                    text = "Tsukaremi",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            navController.navigate(ListDestination) {
                                launchSingleTop = true
                            }
                        }
                        .padding(start = 12.dp)
                )

                Spacer(Modifier.weight(1f))

                IconButton({
                    window.isMinimized = true
                }) {
                    Icon(
                        painterResource(Res.drawable.minimize_window),
                        "Minimize window",
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton({
                    exitApplication()
                }) {
                    Icon(painterResource(Res.drawable.close_window), "Close window", modifier = Modifier.size(20.dp))
                }
            }

            AnimatedContent(
                currentScreen,
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(50.dp),
                transitionSpec = {
                    (slideInVertically(initialOffsetY = { it / 2 }) + fadeIn()) togetherWith (slideOutVertically(
                        targetOffsetY = { it / 2 }) + fadeOut())
                },
                contentKey = { currentScreen }) {
                when (currentScreen) {
                    ListDestination::class.qualifiedName -> {
                        Box(
                            Modifier
                                .padding(4.dp).fillMaxWidth(),
                            contentAlignment = Alignment.BottomEnd,
                        ) {
                            Button(onClick = {
                                navController.navigate(EditDestination()) {
                                    launchSingleTop = true
                                }
                            }) {
                                Text("New Reminder")
                            }
                        }
                    }

                    EditDestination::class.qualifiedName -> {
                        Box(
                            Modifier
                                .padding(4.dp).fillMaxWidth(),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Button(navController::navigateUp, shape = CircleShape) {
                                Text("Back to list")
                            }
                        }
                    }
                }
            }
        } //つかれみ //ツカレミ

        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(
                navController = navController,
                startDestination = ListDestination,
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { it } }
            ) {
                composable<ListDestination> {
                    ListScreen(
                        reminders = reminders,
                        onEdit = {
                            navController.navigate(EditDestination(it.id)) {
                                launchSingleTop = true
                            }
                        },
                        onRestart = {
                            viewModel.viewModelScope.launch {
                                viewModel.reminderService.restartReminder(it)
                            }
                        },
                        onDelete = {
                            viewModel.viewModelScope.launch {
                                viewModel.reminderService.cancelReminder(it)
                                viewModel.repository.deleteReminder(it)
                            }
                        }
                    )
                }

                composable<EditDestination> {
                    EditScreen(
                        it.toRoute<EditDestination>().reminderId,
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