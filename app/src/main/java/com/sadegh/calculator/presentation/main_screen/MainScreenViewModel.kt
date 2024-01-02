package com.sadegh.calculator.presentation.main_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MainScreenViewModel : ViewModel() {

    private val _input= MutableStateFlow("")
    val input=_input.asStateFlow()

    private val _result= MutableStateFlow("")
    val result=_result.asStateFlow()

    fun onEvent(event:UserEvent){

        when(event){
            UserEvent.OnButtonsExpansionButtonClick -> TODO()
            UserEvent.OnClearButtonClick -> TODO()
            UserEvent.OnDeleteButtonClick -> TODO()
            UserEvent.OnDivisionButtonClick -> TODO()
            UserEvent.OnEqualButtonClick -> TODO()
            UserEvent.OnMinusButtonClick -> TODO()
            UserEvent.OnMultiplicationButtonClick -> TODO()
            UserEvent.OnNumber0ButtonClick -> TODO()
            UserEvent.OnNumber1ButtonClick -> TODO()
            UserEvent.OnNumber2ButtonClick -> TODO()
            UserEvent.OnNumber3ButtonClick -> TODO()
            UserEvent.OnNumber4ButtonClick -> TODO()
            UserEvent.OnNumber5ButtonClick -> TODO()
            UserEvent.OnNumber6ButtonClick -> TODO()
            UserEvent.OnNumber7ButtonClick -> TODO()
            UserEvent.OnNumber8ButtonClick -> TODO()
            UserEvent.OnNumber9ButtonClick -> TODO()
            UserEvent.OnPercentButtonClick -> TODO()
            UserEvent.OnPlusButtonClick -> TODO()
            UserEvent.OnPointButtonClick -> TODO()
        }
    }
}