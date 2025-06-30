package com.github.catomon.tsukaremi.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.settings

@Composable
fun SettingsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    IconButton(onClick, modifier = modifier) {
        Icon(painterResource(Res.drawable.settings), "Options", modifier = Modifier.Companion.size(25.dp))
    }
}