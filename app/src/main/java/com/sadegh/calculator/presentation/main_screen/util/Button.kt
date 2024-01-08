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

    object BackspaceButton : Button(
        symbolAsIconId = R.drawable.ic_backspace,
        contentColor = Color.Black,
        color = Color.Gray,
        event = UserEvent.OnBackspaceButtonClick
    )

    sealed class OperatorButton(
        symbolAsString: String,
        symbolAsIconId: Int,
        iconSize: Dp = 30.dp,
        contentColor: Color = Color.White,
        color: Color = operatorColor,
        event: UserEvent
    ) : Button(
        symbolAsString = symbolAsString,
        contentColor = contentColor,
        color = color,
        symbolAsIconId = symbolAsIconId,
        iconSize = iconSize,
        event = event
    ) {

        object SubtractionButton : OperatorButton(
            symbolAsString = "-",
            symbolAsIconId = R.drawable.ic_minus,
            event = UserEvent.OnOperatorButtonClick.OnMinusButtonClick
        )

        object AddButton : OperatorButton(
            symbolAsString = "+",
            symbolAsIconId = R.drawable.ic_add,
            event = UserEvent.OnOperatorButtonClick.OnAddButtonClick
        )

        object MultiplyButton : OperatorButton(
            symbolAsString = "x",
            symbolAsIconId = R.drawable.ic_multiply,
            event = UserEvent.OnOperatorButtonClick.OnMultiplyButtonClick
        )

        object DivisionButton : OperatorButton(
            symbolAsString = "÷",
            symbolAsIconId = R.drawable.ic_divide,
            event = UserEvent.OnOperatorButtonClick.OnDivisionButtonClick
        )

        object PercentageButton : OperatorButton(
            symbolAsString = "%",
            contentColor = Color.Black,
            color = Color.Gray,
            symbolAsIconId = R.drawable.ic_percent,
            event = UserEvent.OnOperatorButtonClick.OnPercentButtonClick
        )
    }

    object EqualButton : Button(
        symbolAsString = "=",
        contentColor = Color.White,
        color = operatorColor,
        symbolAsIconId = R.drawable.ic_equal,
        event = UserEvent.OnEqualButtonClick
    )

    sealed class DigitButton(number: Int, event: UserEvent) : Button(
        symbolAsString = number.toString(),
        event = event
    ) {

        object Digit0Button : DigitButton(
            number = 0,
            event = UserEvent.OnDigitButtonClick.OnDigit0ButtonClick
        )

        object Digit1Button : DigitButton(
            number = 1,
            event = UserEvent.OnDigitButtonClick.OnDigit1ButtonClick
        )

        object Digit2Button : DigitButton(
            number = 2,
            event = UserEvent.OnDigitButtonClick.OnDigit2ButtonClick
        )

        object Digit3Button : DigitButton(
            number = 3,
            event = UserEvent.OnDigitButtonClick.OnDigit3ButtonClick
        )

        object Digit4Button : DigitButton(
            number = 4,
            event = UserEvent.OnDigitButtonClick.OnDigit4ButtonClick
        )

        object Digit5Button : DigitButton(
            number = 5,
            event = UserEvent.OnDigitButtonClick.OnDigit5ButtonClick
        )

        object Digit6Button : DigitButton(
            number = 6,
            event = UserEvent.OnDigitButtonClick.OnDigit6ButtonClick
        )

        object Digit7Button : DigitButton(
            number = 7,
            event = UserEvent.OnDigitButtonClick.OnDigit7ButtonClick
        )

        object Digit8Button : DigitButton(
            number = 8,
            event = UserEvent.OnDigitButtonClick.OnDigit8ButtonClick
        )

        object Digit9Button : DigitButton(
            number = 9,
            event = UserEvent.OnDigitButtonClick.OnDigit9ButtonClick
        )
    }

    object PointButton : Button(symbolAsString = ".", event = UserEvent.OnPointButtonClick)

    object ExpansionButton : Button(
        symbolAsIconId = R.drawable.ic_show_more_operator,
        event = UserEvent.OnButtonsExpansionButtonClick
    )

    object UndefinedButton : Button(symbolAsString = "", event = null)
}
