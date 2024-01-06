package com.sadegh.calculator.presentation.main_screen

sealed interface UserEvent {

    object OnClearButtonClick : UserEvent
    object OnDeleteButtonClick : UserEvent
    sealed class OnOperatorButtonClick(val symbol: String) : UserEvent {
        object OnDivisionButtonClick : OnOperatorButtonClick("÷")
        object OnMultiplyButtonClick : OnOperatorButtonClick("x")
        object OnAddButtonClick : OnOperatorButtonClick("+")
        object OnMinusButtonClick : OnOperatorButtonClick("-")
        object OnPercentButtonClick : OnOperatorButtonClick("%")
    }

    object OnEqualButtonClick : UserEvent
    data class OnNumberButtonClick(val number: Int) : UserEvent
    object OnPointButtonClick : UserEvent
    object OnButtonsExpansionButtonClick : UserEvent


}