package com.sadegh.calculator.presentation.main_screen.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.calculator.R
import com.sadegh.calculator.presentation.main_screen.UserEvent
import com.sadegh.calculator.ui.theme.operatorColor

sealed class Button(
    val symbolAsString: String? = null,
    val symbolAsIconId: Int? = null,
    val iconSize: Dp = 30.dp,
    val contentColor: Color = Color.White,
    val color: Color = Color.DarkGray,
    val event: UserEvent?
) {

    object Clear :
        Button(
            symbolAsString = "C",
            contentColor = Color.Black,
            color = Color.Gray,
            event = UserEvent.OnClearButtonClick
        )

    object Percent :
        Button(
            contentColor = Color.Black,
            color = Color.Gray,
            symbolAsIconId = R.drawable.ic_percent,
            event = UserEvent.OnPercentButtonClick
        )

    object Delete :
        Button(
            symbolAsIconId = R.drawable.ic_backspace,
            contentColor = Color.Black,
            color = Color.Gray,
            event = UserEvent.OnDeleteButtonClick
        )

    class Operator(symbol: String, iconId: Int) :
        Button(
            symbolAsString = symbol,
            contentColor = Color.White,
            color = operatorColor,
            symbolAsIconId = iconId,
            event = UserEvent.OnOperatorButtonClick(symbol = symbol)
        )

    object Equal :
        Button(
            symbolAsString = "=",
            contentColor = Color.White,
            color = operatorColor,
            symbolAsIconId = R.drawable.ic_equal,
            event = UserEvent.OnEqualButtonClick
        )

    class Number(number: Int) :
        Button(
            symbolAsString = number.toString(),
            event = UserEvent.OnNumberButtonClick(number)
        )

    object Point : Button(symbolAsString = ".", event = UserEvent.OnPointButtonClick)
    object Expansion : Button(
        symbolAsIconId = R.drawable.ic_show_more_operator,
        event = UserEvent.OnButtonsExpansionButtonClick
    )

    object Undefined : Button(symbolAsString = "", event = null)
}
