package com.github.catomon.tsukaremi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import com.github.catomon.tsukaremi.ui.compositionlocals.LocalNavController
import kotlinx.serialization.Serializable

@Serializable
object SettingsDestination {
    override fun toString(): String {
        return "settings"
    }
}

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onExitApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier) {
        Box(modifier = modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Settings", modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onBack) {
                        Text("BACK")
                    }

                    TextButton(onClick = onExitApp) {
                        Text("Exit App")
                    }
                }
            }
        }
    }
}
