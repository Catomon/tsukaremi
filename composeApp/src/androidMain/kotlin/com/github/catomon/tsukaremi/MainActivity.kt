package com.github.catomon.tsukaremi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.github.catomon.tsukaremi.ui.screens.TsukaremiMainScreen
import com.github.catomon.tsukaremi.ui.theme.TsukaremiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TsukaremiTheme {
                Scaffold {
                    TsukaremiMainScreen(modifier = Modifier.padding(it))
                }
            }
        }
    }
}
