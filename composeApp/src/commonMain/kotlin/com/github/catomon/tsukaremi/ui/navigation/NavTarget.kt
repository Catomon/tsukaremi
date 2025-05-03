package com.github.catomon.tsukaremi.ui.navigation

sealed interface NavTarget {
    data class NavigateTo(val route: String) : NavTarget
    object PopBack : NavTarget
}