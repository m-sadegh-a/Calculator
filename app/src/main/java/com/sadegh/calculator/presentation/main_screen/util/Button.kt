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

    object ClearButton : Button(
        symbolAsString = "C",
        contentColor = Color.Black,
        color = Color.Gray,
        event = UserEvent.OnClearButtonClick
    )

    object DeleteButton : Button(
        symbolAsIconId = R.drawable.ic_backspace,
        contentColor = Color.Black,
        color = Color.Gray,
        event = UserEvent.OnDeleteButtonClick
    )

    sealed class OperatorButton(
        symbolAsString: String,
        symbolAsIconId: Int,
        iconSize: Dp = 30.dp,
        contentColor: Color = Color.White,
        color: Color = operatorColor,
    ) : Button(
        symbolAsString = symbolAsString,
        contentColor = contentColor,
        color = color,
        symbolAsIconId = symbolAsIconId,
        iconSize = iconSize,
        event = UserEvent.OnOperatorButtonClick(symbol = symbolAsString)
    ) {

        object SubtractionButton : OperatorButton(
            symbolAsString = "-",
            symbolAsIconId = R.drawable.ic_minus,
        )

        object AddButton : OperatorButton(
            symbolAsString = "+",
            symbolAsIconId = R.drawable.ic_add,
        )

        object MultiplyButton : OperatorButton(
            symbolAsString = "x",
            symbolAsIconId = R.drawable.ic_multiply,
        )

        object DivisionButton : OperatorButton(
            symbolAsString = "÷",
            symbolAsIconId = R.drawable.ic_divide,
        )

        object PercentageButton : OperatorButton(
            symbolAsString = "%",
            contentColor = Color.Black,
            color = Color.Gray,
            symbolAsIconId = R.drawable.ic_percent,
        )
    }

    object EqualButton : Button(
        symbolAsString = "=",
        contentColor = Color.White,
        color = operatorColor,
        symbolAsIconId = R.drawable.ic_equal,
        event = UserEvent.OnEqualButtonClick
    )

    sealed class DigitButton(number: Int) : Button(
        symbolAsString = number.toString(),
        event = UserEvent.OnNumberButtonClick(number)
    ) {

        object Digit0Button : DigitButton(0)
        object Digit1Button : DigitButton(1)
        object Digit2Button : DigitButton(2)
        object Digit3Button : DigitButton(3)
        object Digit4Button : DigitButton(4)
        object Digit5Button : DigitButton(5)
        object Digit6Button : DigitButton(6)
        object Digit7Button : DigitButton(7)
        object Digit8Button : DigitButton(8)
        object Digit9Button : DigitButton(9)
    }

    object PointButton : Button(symbolAsString = ".", event = UserEvent.OnPointButtonClick)

    object ExpansionButton : Button(
        symbolAsIconId = R.drawable.ic_show_more_operator,
        event = UserEvent.OnButtonsExpansionButtonClick
    )

    object UndefinedButton : Button(symbolAsString = "", event = null)
}
