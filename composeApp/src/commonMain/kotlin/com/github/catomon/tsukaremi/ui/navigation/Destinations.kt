package com.github.catomon.tsukaremi.ui.navigation

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
data class EditDestination(val reminderId: Int? = null)

@Serializable
object ListDestination

@Serializable
object SettingsDestination

fun navigateToSettings(currentScreen: String?, navController: NavHostController) {
    if (currentScreen == SettingsDestination::class.qualifiedName)
        navController.navigateUp()
    else
        navController.navigate(SettingsDestination) {
            launchSingleTop = true
        }
}
