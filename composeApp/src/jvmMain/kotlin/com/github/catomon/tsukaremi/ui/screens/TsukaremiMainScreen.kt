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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.catomon.tsukaremi.ui.components.OutlinedText
import com.github.catomon.tsukaremi.ui.components.SettingsButton
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalNavController
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalWindow
import com.github.catomon.tsukaremi.ui.effect.Starfall
import com.github.catomon.tsukaremi.ui.navigation.EditDestination
import com.github.catomon.tsukaremi.ui.navigation.ListDestination
import com.github.catomon.tsukaremi.ui.navigation.SettingsDestination
import com.github.catomon.tsukaremi.ui.navigation.navigateToSettings
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme
import com.github.catomon.tsukaremi.ui.util.darken
import com.github.catomon.tsukaremi.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.star
import tsukaremi.composeapp.generated.resources.top_bar_background
import tsukaremi.composeapp.generated.resources.top_bar_background_overlay
import tsukaremi.composeapp.generated.resources.tsukasa_sleepy
import kotlin.system.exitProcess

@Composable
fun TsukaremiMainScreen(
    viewModel: MainViewModel = koinViewModel(),
    onCloseRequest: () -> Unit = { exitProcess(0) },
    modifier: Modifier = Modifier
) = Surface(modifier = modifier) {
    val window = LocalWindow.current
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen by
    remember(currentBackStackEntry) {
        mutableStateOf(
            currentBackStackEntry?.destination?.route?.split("?")?.firstOrNull()
        )
    }

    val reminders by viewModel.reminders.collectAsState()

    val appSettings by viewModel.appSettings.collectAsState()

    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            TsukaremiTheme.colors.background2,
            TsukaremiTheme.colors.background2,
        ),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset.Infinite
    )

    Box(
        modifier = Modifier.fillMaxSize().background(gradientBrush),
    ) {
        if (viewModel.appSettings.value.showEffects)
            Starfall(imageResource(Res.drawable.star))

        Image(
            painter = painterResource(Res.drawable.tsukasa_sleepy),
            modifier = Modifier.align(Alignment.BottomEnd),//.offset(y = 20.dp),
            contentDescription = null
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                .padding(horizontal = 6.dp)
                    .height(100.dp)
                    .background(color = TsukaremiTheme.colors.characterColor.darken(1.2f), shape = RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp))
                    .border(
                        color = TsukaremiTheme.colors.characterColor.darken(1.2f),
                        width = 4.dp,
                        shape = RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
                    ),
            ) {
                Image(
                    painterResource(Res.drawable.top_bar_background),
                    null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

//                Image(
//                    painterResource(Res.drawable.top_bar_background_overlay),
//                    null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.matchParentSize().clip(
//                        RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
//                    ),
//                    colorFilter = ColorFilter.tint(TsukaremiTheme.colors.characterColor)
//                )

                SettingsButton({
                    navigateToSettings(currentScreen, navController)
                }, Modifier.align(Alignment.BottomStart))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.TopEnd)) {
                    OutlinedText(
                        text = "Tsukaremi",
                        outlineColor = TsukaremiTheme.colors.characterColor,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                navController.navigate(ListDestination) {
                                    launchSingleTop = true
                                }
                            }
                            .padding(start = 12.dp),
                    )

                    Spacer(Modifier.weight(1f).height(40.dp))

                    OutlinedText(
                        text = "-",
                        outlineColor = TsukaremiTheme.colors.characterColor,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                window.isMinimized = true
                            }
                            .padding(horizontal = 12.dp),
                        outlineSize = 6f
                    )

                    HideInTrayButton(modifier = Modifier.padding(end = 12.dp)) {
                        onCloseRequest()
                    }
                }

                MainScreenNavButton(currentScreen, navController, Modifier.align(Alignment.Companion.BottomCenter))
            }

            CompositionLocalProvider(LocalNavController provides navController) {
                NavHost(
                    navController = navController,
                    startDestination = ListDestination,
                    enterTransition = { slideInHorizontally { it } },
                    exitTransition = { slideOutHorizontally { -it } }
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
                                    viewModel.reminderManager.restartReminder(it)
                                }
                            },
                            onDelete = {
                                viewModel.viewModelScope.launch {
                                    viewModel.reminderManager.cancelReminder(it)
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
                        SettingsScreen(appSettings, {
                            viewModel.updateSettings(it)
                            viewModel.saveSettings()
                        })
                    }
                }
            }
        }
    }
}

@Composable
private fun HideInTrayButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.size(14.dp)
            .background(color = TsukaremiTheme.colors.characterColor, shape = CircleShape).clickable { onClick() }
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier.size(8.dp)
                .background(color = Color.White, shape = CircleShape)
        )
    }
}

@Composable
fun MainScreenNavButton(
    currentScreen: String?,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        currentScreen,
        modifier = modifier.fillMaxWidth().height(50.dp),
        transitionSpec = {
            (slideInVertically(initialOffsetY = { it / 2 }) + fadeIn()) togetherWith (slideOutVertically(
                targetOffsetY = { it / 2 }) + fadeOut())
        },
        contentKey = { currentScreen }) {
        when (currentScreen) {
            ListDestination::class.qualifiedName -> {
                Box(
                    Modifier.Companion
                        .padding(4.dp).fillMaxWidth(),
                    contentAlignment = Alignment.Companion.BottomEnd,
                ) {
                    Box(Modifier.clickable {
                        navController.navigate(EditDestination()) {
                            launchSingleTop = true
                        }
                    }.width(60.dp), contentAlignment = Alignment.Center) {
                        OutlinedText(
                            "New",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            outlineColor = TsukaremiTheme.colors.characterColor,
                            outlineSize = 4f
                        )
                    }
                }
            }

            EditDestination::class.qualifiedName -> {
                Box(Modifier.padding(4.dp).fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                    Box(Modifier.clickable {
                        navController.navigateUp()
                    }.width(60.dp), contentAlignment = Alignment.Center) {
                        OutlinedText(
                            "Back",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            outlineColor = TsukaremiTheme.colors.characterColor
                        )
                    }
                }
            }

            SettingsDestination::class.qualifiedName -> {
                Box(
                    Modifier.Companion
                        .padding(4.dp).fillMaxWidth(),
                    contentAlignment = Alignment.Companion.BottomEnd
                ) {
                    Box(Modifier.clickable {
                        navController.navigateUp()
                    }.width(60.dp), contentAlignment = Alignment.Center) {
                        OutlinedText(
                            text = "Save",
                            outlineColor = TsukaremiTheme.colors.characterColor,
                        )
                    }
                }
            }
        }
    }
}
