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
}