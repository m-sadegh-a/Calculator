package com.sadegh.calculator.homeScreen

import androidx.compose.ui.graphics.Color

data class Button(val symbol: String) {

    val weight: Float
        get() = if (symbol == "AC" || symbol == "0") 2f else 1f

    val color: Color
        get() =
            when (symbol) {

                "AC", "DEL" -> Color.Gray

                in arrayOf("/", "x", "-", "+", "=") -> Color(0xFFFF8C01)

                else -> Color.DarkGray

            }
}
