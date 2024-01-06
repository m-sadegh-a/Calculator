package com.sadegh.calculator.presentation.main_screen.util

val buttons = listOf(
    listOf(
        Button.UndefinedButton,
        Button.UndefinedButton,
        Button.UndefinedButton,
        Button.UndefinedButton,
        Button.UndefinedButton,
    ),
    listOf(
        Button.UndefinedButton,
        Button.ClearButton,
        Button.OperatorButton.PercentageButton,
        Button.DeleteButton,
        Button.OperatorButton.DivisionButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.DigitButton.Digit7Button,
        Button.DigitButton.Digit8Button,
        Button.DigitButton.Digit9Button,
        Button.OperatorButton.MultiplyButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.DigitButton.Digit4Button,
        Button.DigitButton.Digit5Button,
        Button.DigitButton.Digit6Button,
        Button.OperatorButton.SubtractionButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.DigitButton.Digit1Button,
        Button.DigitButton.Digit2Button,
        Button.DigitButton.Digit3Button,
        Button.OperatorButton.AddButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.ExpansionButton,
        Button.DigitButton.Digit0Button,
        Button.PointButton,
        Button.EqualButton
    ),
)

object ResultType {

    const val UNDEFINED = "can not divide by zero"

}