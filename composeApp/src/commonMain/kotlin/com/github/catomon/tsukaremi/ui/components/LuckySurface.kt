package com.github.catomon.tsukaremi.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import tsukaremi.composeapp.generated.resources.Res
import tsukaremi.composeapp.generated.resources.background

@Composable
fun LuckySurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) =
    Surface(modifier) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(Res.drawable.background),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(14.dp)),
                contentDescription = null,
//                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
            )

            content()
        }
    }