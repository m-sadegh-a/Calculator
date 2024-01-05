package com.sadegh.calculator.presentation.main_screen

sealed interface UserEvent {

    object OnClearButtonClick : UserEvent
    object OnPercentButtonClick : UserEvent
    object OnDeleteButtonClick : UserEvent
    data class OnOperatorButtonClick(val symbol: String) : UserEvent
    object OnEqualButtonClick : UserEvent
    data class OnNumberButtonClick(val number: Int) : UserEvent
    object OnPointButtonClick : UserEvent
    object OnButtonsExpansionButtonClick : UserEvent


}