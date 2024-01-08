package com.sadegh.calculator.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun ResultBox(
    modifier: Modifier,
    topText: String,
    topTextFontSize: TextUnit,
    topTextColor: Color,
    bottomText: String,
    bottomTextFontSize: TextUnit,
    bottomTextColor: Color,
) {

    LazyColumn(
        modifier
            .fillMaxSize()
            .scale(scaleX = 1f, scaleY = -1f),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scaleX = 1f, scaleY = -1f),
                contentAlignment = Alignment.CenterEnd
            ) {


                Text(
                    text = bottomText,
                    fontSize = bottomTextFontSize,
                    color = bottomTextColor,
                    maxLines = 1
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scaleX = 1f, scaleY = -1f),
                contentAlignment = Alignment.BottomEnd
            ) {


                Text(
                    text = topText,
                    fontSize = topTextFontSize,
                    color = topTextColor,
                    maxLines = Int.MAX_VALUE,
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}