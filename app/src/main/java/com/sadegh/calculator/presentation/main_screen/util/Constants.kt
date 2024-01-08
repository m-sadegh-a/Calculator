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
        Button.BackspaceButton,
        Button.OperatorButton.DivisionButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.NumberButton.Digit7Button,
        Button.NumberButton.Digit8Button,
        Button.NumberButton.Digit9Button,
        Button.OperatorButton.MultiplyButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.NumberButton.Digit4Button,
        Button.NumberButton.Digit5Button,
        Button.NumberButton.Digit6Button,
        Button.OperatorButton.SubtractionButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.NumberButton.Digit1Button,
        Button.NumberButton.Digit2Button,
        Button.NumberButton.Digit3Button,
        Button.OperatorButton.AddButton
    ),
    listOf(
        Button.NumberButton.NeperNumberButton,
        Button.ExpansionButton,
        Button.NumberButton.Digit0Button,
        Button.PointButton,
        Button.EqualButton
    ),
)

object ResultType {

    const val UNDEFINED = "can not divide by zero"

}