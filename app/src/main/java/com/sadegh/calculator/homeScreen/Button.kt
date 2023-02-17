package com.sadegh.calculator.homeScreen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Button(
    val symbolAsString: String,
    val symbolAsIconId: Int? = null,
    val iconSize: Dp = 30.dp,
    val contentColor: Color = Color.White,
    val weight: Float = 1f,
    val color: Color = Color.DarkGray
)
