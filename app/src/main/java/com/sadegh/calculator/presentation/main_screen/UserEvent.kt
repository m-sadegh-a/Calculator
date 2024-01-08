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
    sealed class OnDigitButtonClick(val number: Int) : UserEvent{

        object OnDigit0ButtonClick:OnDigitButtonClick(0)
        object OnDigit1ButtonClick:OnDigitButtonClick(1)
        object OnDigit2ButtonClick:OnDigitButtonClick(2)
        object OnDigit3ButtonClick:OnDigitButtonClick(3)
        object OnDigit4ButtonClick:OnDigitButtonClick(4)
        object OnDigit5ButtonClick:OnDigitButtonClick(5)
        object OnDigit6ButtonClick:OnDigitButtonClick(6)
        object OnDigit7ButtonClick:OnDigitButtonClick(7)
        object OnDigit8ButtonClick:OnDigitButtonClick(8)
        object OnDigit9ButtonClick:OnDigitButtonClick(9)
    }
    object OnPointButtonClick : UserEvent
    object OnButtonsExpansionButtonClick : UserEvent


}