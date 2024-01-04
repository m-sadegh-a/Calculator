package com.sadegh.calculator.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.sadegh.calculator.homeScreen.Operator
import com.sadegh.calculator.homeScreen.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MainScreenViewModel : ViewModel() {

    private val _input = MutableStateFlow(mutableListOf<String>())
    val input = _input.asStateFlow()

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    private val _startIndex = MutableStateFlow(1)
    val startIndex = _startIndex.asStateFlow()

    private
    val lastElement: String
        get() = input.value.last()

    private fun isResultUndefined() = result.value == ResultType.undefined
    private fun isClean() = input.value == mutableListOf("0")
    private fun isLastInputAnOperator() = lastElement in arrayOf("÷", "x", "-", "+")
    private fun isLastInputEqual() = lastElement == "="
    private fun isLastInputPercent() = lastElement == "%"

    fun onEvent(event: UserEvent) {

        when (event) {
            UserEvent.OnButtonsExpansionButtonClick -> TODO()
            UserEvent.OnClearButtonClick -> onClearButtonClick()
            UserEvent.OnDeleteButtonClick -> onDeleteButtonClick()
            UserEvent.OnEqualButtonClick -> onEqualButtonClick()
            UserEvent.OnPercentButtonClick -> onPercentButtonClick()
            UserEvent.OnPointButtonClick -> onPointButtonClick()
            is UserEvent.OnNumberButtonClick -> onNumberButtonClick(event.number)
            is UserEvent.OnOperatorButtonClick -> onOperatorButtonClick(event.symbol)
        }
    }

    private fun onButtonsExpansionButtonClick() {

        _startIndex.value = if (_startIndex.value == 1) {
            0
        } else {
            1
        }
    }

    private fun onClearButtonClick() {

        _input.value = mutableListOf("0")
        _result.value = ""
    }

    private fun onPercentButtonClick() {

        if (isLastInputEqual()) {
            _input.value = if (result.value != ResultType.undefined) {
                mutableListOf(result.value, "%")
            } else {
                mutableListOf("0", "%")
            }
            return
        }

        if (lastElement.last() == '.') {

            _input.value[input.value.lastIndex] = lastElement.dropLast(1)

        }

        _input.value = (_input.value + "%").toMutableList()

        _result.value = calculateResult()
    }

    private fun onDeleteButtonClick() {

        when {

            isLastInputEqual() -> return

            _input.value.singleOrNull()?.length == 1 -> _input.value = mutableListOf("0")

            lastElement.length == 1 -> _input.value.removeAt(_input.value.lastIndex)

            else -> _input.value[_input.value.lastIndex] = lastElement.dropLast(1)

        }
        _result.value = calculateResult()
    }

    private fun onOperatorButtonClick(operatorSymbol: String) {

        if (isLastInputEqual()) {
            _input.value = if (_result.value != ResultType.undefined) {
                mutableListOf(_result.value, operatorSymbol)
            } else {
                mutableListOf("0", operatorSymbol)
            }
            return
        }

        if (lastElement.last() == '.') {

            val lastElement = lastElement.dropLast(1)
            _input.value = (_input.value.dropLast(1) + lastElement).toMutableList()

        }

        if (isLastInputAnOperator()) {

            _input.value = (_input.value.dropLast(1) + operatorSymbol).toMutableList()
        } else {

            _input.value = (_input.value + operatorSymbol).toMutableList()
        }
    }

    private fun onNumberButtonClick(number: Int) {

        when {

            lastElement.length == 15 -> return

            isLastInputEqual() || isClean() -> _input.value = mutableListOf(number.toString())

            isLastInputAnOperator() || isLastInputPercent() -> {

                _input.value = (_input.value + number.toString()).toMutableList()
            }

            else -> {
                val newLastElement = "$lastElement$number"
                _input.value = (_input.value.dropLast(1) + newLastElement).toMutableList()
            }
        }

        _result.value = calculateResult()
    }

    private fun onPointButtonClick() {

        when {
            lastElement.length == 15 || "." in lastElement -> return

            isLastInputEqual() -> _input.value = mutableListOf("0.")

            isLastInputAnOperator() || isLastInputPercent() -> {
                _input.value = (_input.value + "0.").toMutableList()
            }

            else -> {
                val newLastElement = "$lastElement."
                _input.value = (_input.value + newLastElement).toMutableList()
            }
        }
    }

    private fun onEqualButtonClick() {

        if (isLastInputEqual()) {
            return
        }

        if (lastElement.last() == '.') {

            _input.value = (_input.value.dropLast(1)).toMutableList()

        }
        _input.value = (_input.value + "=").toMutableList()

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