package com.github.catomon.tsukaremi.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun OutlinedText(
    text: String,
    color: Color = Color.White,
    outlineColor: Color = Color.Black,
    fontSize: TextUnit = TextUnit.Unspecified,
    outlineSize: Float = 6f,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    lineHeight: TextUnit = TextUnit.Unspecified,
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Text(
            text,
            style =
                LocalTextStyle.current.merge(
                    TextStyle(
                        color = outlineColor,
                        drawStyle = Stroke(
                            width = outlineSize,
                            join = StrokeJoin.Round
                        )
                    )

                ),
            fontSize = fontSize,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow,
            lineHeight = lineHeight
        )
        Text(
            text, fontSize = fontSize, color = color, textAlign = textAlign, maxLines = maxLines,
            overflow = overflow,
            lineHeight = lineHeight
        )
    }
}