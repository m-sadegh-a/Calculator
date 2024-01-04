package com.sadegh.calculator.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.sadegh.calculator.homeScreen.Operator
import com.sadegh.calculator.homeScreen.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

@HiltViewModel
class MainScreenViewModel : ViewModel() {

    private val _input = MutableStateFlow(emptyList<String>())
    val input = _input.asStateFlow()

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    private
    val lastElement: String
        get() = input.value.last()

    private fun isResultUndefined() = result.value == ResultType.undefined
    private fun isClean() = input.value == mutableListOf("0")
    private fun isLastInputAnOperator() = lastElement in arrayOf("÷", "x", "-", "+")

    fun onEvent(event: UserEvent) {

        when (event) {
            UserEvent.OnButtonsExpansionButtonClick -> TODO()
            UserEvent.OnClearButtonClick -> onClearButtonClick()
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

    private fun onClearButtonClick() {

        _input.value = mutableListOf("0")
        _result.value = ""
    }

    private fun calculateResult(): String {

        if (isResultUndefined()) {
            return result.value
        }

        if (isClean()) {
            return ""
        }

        if (input.value.size == 1) {
            return (input.value.single().toDouble() * 1).toString()
        }

        val operatorsSymbol = arrayOf(
            arrayOf("÷", "x", "%"),
            arrayOf("-", "+")
        )

        val newInput = input.value.dropLast(if (isLastInputAnOperator()) 1 else 0).toMutableList()

        operatorsSymbol.forEach { operatorsWithSamePrecedence ->

            var index = 0
            while (index < newInput.size) {

                val numberOrOperator = newInput[index]

                if (
                    numberOrOperator == operatorsWithSamePrecedence[0] ||
                    numberOrOperator == operatorsWithSamePrecedence[1] ||
                    numberOrOperator == operatorsWithSamePrecedence.getOrNull(2)
                ) {

                    val number1 = newInput[index - 1].toDouble()
                    var number2 = newInput.getOrNull(index + 1)?.toDoubleOrNull()

                    if (numberOrOperator == "%" && number2 == null) {
                        newInput.add(index = index + 1, "1")
                        number2 = 1.0
                    }

                    if (number2 == 0.0 && numberOrOperator == "÷") {
                        return ResultType.undefined
                    }

                    val operator = Operator(numberOrOperator)
                    val result = operator.operate(number1, number2!!).toString()

                    newInput[index + 1] = result
                    newInput.removeAt(index)
                    newInput.removeAt(index - 1)

                } else {

                    index++

                }
            }
        }

        return newInput.single()
    }
}