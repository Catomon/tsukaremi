package com.github.catomon.tsukaremi.ui.navigation

sealed interface NavTarget {
    data class NavigateTo<T>(val route: T) : NavTarget
    data object PopBack : NavTarget
}