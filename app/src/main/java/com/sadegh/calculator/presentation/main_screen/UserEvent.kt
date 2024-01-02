package com.sadegh.calculator.presentation.main_screen

sealed interface UserEvent {

    object OnClearButtonClick : UserEvent
    object OnPercentButtonClick : UserEvent
    object OnDeleteButtonClick : UserEvent
    object OnDivisionButtonClick : UserEvent
    object OnMultiplicationButtonClick : UserEvent
    object OnMinusButtonClick : UserEvent
    object OnPlusButtonClick : UserEvent
    object OnEqualButtonClick : UserEvent
    object OnNumber0ButtonClick : UserEvent
    object OnNumber1ButtonClick : UserEvent
    object OnNumber2ButtonClick : UserEvent
    object OnNumber3ButtonClick : UserEvent
    object OnNumber4ButtonClick : UserEvent
    object OnNumber5ButtonClick : UserEvent
    object OnNumber6ButtonClick : UserEvent
    object OnNumber7ButtonClick : UserEvent
    object OnNumber8ButtonClick : UserEvent
    object OnNumber9ButtonClick : UserEvent
    object OnPointButtonClick:UserEvent
    object OnButtonsExpansionButtonClick:UserEvent


}