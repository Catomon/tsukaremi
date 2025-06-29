package com.github.catomon.tsukaremi.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalNavController
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.close_window
import tsukaremi.composeapp.generated.resources.settings
import tsukaremi.composeapp.generated.resources.top_bar_background

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TsukaremiMainScreen(
    padding: PaddingValues,
    viewModel: MainViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) = Surface(modifier = modifier) {
    val navController = rememberNavController()
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
                modifier = Modifier
                    .matchParentSize()
                    .clip(
                        RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
                    )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = padding.calculateTopPadding())
            ) {
                Text(
                    text = "Tsukaremi",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            navController.popBackStack(ListDestination, false, false)
                        }
                        .padding(start = 12.dp)
                )

                Spacer(Modifier.weight(1f))

                IconButton({

                }) {
                    Icon(painterResource(Res.drawable.settings), "Options", modifier = Modifier.size(25.dp))
                }
            }
        } //つかれみ //ツカレミ

        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(
                navController = navController,
                startDestination = ListDestination,
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { it } },
            ) {
                composable<ListDestination> {
                    ListScreen(
                        reminders = reminders, onCreateNew = {
                            navController.navigate(EditDestination()) {
                                launchSingleTop = true
                            }
                        },
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
                        },
                        padding = padding
                    )
                }

                composable<EditDestination> {
                    EditScreen(
                        it.toRoute<EditDestination>().reminderId,
                        onBack = navController::navigateUp,
                        onConfirm = navController::navigateUp,
                        padding = padding,
                    )
                }

                composable<SettingsDestination> {
                    SettingsScreen(
                        onBack = navController::navigateUp,
                    )
                }
            }
        }
    }
}