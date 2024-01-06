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
        Button.DigitButton(7),
        Button.DigitButton(8),
        Button.DigitButton(9),
        Button.OperatorButton.MultiplyButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.DigitButton(4),
        Button.DigitButton(5),
        Button.DigitButton(6),
        Button.OperatorButton.SubtractionButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.DigitButton(1),
        Button.DigitButton(2),
        Button.DigitButton(3),
        Button.OperatorButton.AddButton
    ),
    listOf(
        Button.UndefinedButton,
        Button.ExpansionButton,
        Button.DigitButton(0),
        Button.PointButton,
        Button.EqualButton
    ),
)

object ResultType {

    const val UNDEFINED = "can not divide by zero"

}