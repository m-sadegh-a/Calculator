package com.sadegh.calculator.presentation.main_screen

import com.sadegh.calculator.presentation.main_screen.util.Input

sealed interface UserEvent {

    object OnClearButtonClick : UserEvent
    object OnBackspaceButtonClick : UserEvent
    sealed class OnOperatorButtonClick(val operator: Input.OperatorInput) : UserEvent {
        object OnDivisionButtonClick :
            OnOperatorButtonClick(Input.OperatorInput.BasicOperatorInput.DivisionOperatorInput)

        object OnMultiplyButtonClick :
            OnOperatorButtonClick(Input.OperatorInput.BasicOperatorInput.MultiplyOperatorInput)

        object OnAddButtonClick :
            OnOperatorButtonClick(Input.OperatorInput.BasicOperatorInput.AddOperatorInput)

        object OnMinusButtonClick :
            OnOperatorButtonClick(Input.OperatorInput.BasicOperatorInput.SubtractOperatorInput)

        object OnPercentButtonClick :
            OnOperatorButtonClick(Input.OperatorInput.PercentageOperatorInput)
    }

    object OnEqualButtonClick : UserEvent
    sealed class OnNumberButtonClick(val number: Input.Number) : UserEvent {

        object OnDigit0ButtonClick : OnNumberButtonClick(Input.Number("0"))
        object OnDigit1ButtonClick : OnNumberButtonClick(Input.Number("1"))
        object OnDigit2ButtonClick : OnNumberButtonClick(Input.Number("2"))
        object OnDigit3ButtonClick : OnNumberButtonClick(Input.Number("3"))
        object OnDigit4ButtonClick : OnNumberButtonClick(Input.Number("4"))
        object OnDigit5ButtonClick : OnNumberButtonClick(Input.Number("5"))
        object OnDigit6ButtonClick : OnNumberButtonClick(Input.Number("6"))
        object OnDigit7ButtonClick : OnNumberButtonClick(Input.Number("7"))
        object OnDigit8ButtonClick : OnNumberButtonClick(Input.Number("8"))
        object OnDigit9ButtonClick : OnNumberButtonClick(Input.Number("9"))
        object OnNeperNumberButtonClick : OnNumberButtonClick(Input.Number.NeperNumber)
    }

    object OnPointButtonClick : UserEvent
    object OnButtonsExpansionButtonClick : UserEvent


}