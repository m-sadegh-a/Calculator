package com.sadegh.calculator.presentation.main_screen

sealed interface UserEvent {

    object OnClearButtonClick : UserEvent
    object OnBackspaceButtonClick : UserEvent
    sealed class OnOperatorButtonClick(val symbol: String) : UserEvent {
        object OnDivisionButtonClick : OnOperatorButtonClick("÷")
        object OnMultiplyButtonClick : OnOperatorButtonClick("x")
        object OnAddButtonClick : OnOperatorButtonClick("+")
        object OnMinusButtonClick : OnOperatorButtonClick("-")
        object OnPercentButtonClick : OnOperatorButtonClick("%")
    }

    object OnEqualButtonClick : UserEvent
    sealed class OnNumberButtonClick(val number: String) : UserEvent {

        object OnDigit0ButtonClick : OnNumberButtonClick("0")
        object OnDigit1ButtonClick : OnNumberButtonClick("1")
        object OnDigit2ButtonClick : OnNumberButtonClick("2")
        object OnDigit3ButtonClick : OnNumberButtonClick("3")
        object OnDigit4ButtonClick : OnNumberButtonClick("4")
        object OnDigit5ButtonClick : OnNumberButtonClick("5")
        object OnDigit6ButtonClick : OnNumberButtonClick("6")
        object OnDigit7ButtonClick : OnNumberButtonClick("7")
        object OnDigit8ButtonClick : OnNumberButtonClick("8")
        object OnDigit9ButtonClick : OnNumberButtonClick("9")
        object OnNeperNumberButtonClick:OnNumberButtonClick("e")
    }

    object OnPointButtonClick : UserEvent
    object OnButtonsExpansionButtonClick : UserEvent


}