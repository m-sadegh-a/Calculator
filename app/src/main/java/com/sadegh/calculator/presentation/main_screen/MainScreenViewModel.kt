package com.sadegh.calculator.presentation.main_screen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadegh.calculator.homeScreen.Operator
import com.sadegh.calculator.homeScreen.ResultType
import com.sadegh.calculator.homeScreen.formatInput
import com.sadegh.calculator.homeScreen.formatResult
import com.sadegh.calculator.homeScreen.separateAllThreeDigitsOfNumberWithComma
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(): ViewModel() {

    private val _input = MutableStateFlow(mutableListOf("0"))

    val input = _input.transform {

        val formattedInput = it.separateAllThreeDigitsOfNumberWithComma().joinToString("")
            .dropLast(if (isLastInputEqual.value) 1 else 0).formatInput()
        emit(formattedInput)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            "0"
        )

    private val _result = MutableStateFlow("")
    val result = _result
        .transform {

            val formattedResult = mutableListOf(it)
                .separateAllThreeDigitsOfNumberWithComma()
                .single()
                .formatResult()

            emit(formattedResult)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

    private val _startIndex = MutableStateFlow(1)
    val startIndex = _startIndex.asStateFlow()

    private val lastElement = _input
        .transform { emit(it.last()) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            "0"
        )


    private val isLastInputEqual = lastElement
        .transform { emit(it == "=") }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )


    val inputFontSize = isLastInputEqual
        .transform {

            val length = _input.value.size

            val fontSizeMap = mapOf(
                12 to 45.4.sp,
                13 to 42.5.sp,
                14 to 39.sp,
                15 to 36.7.sp,
                16 to 34.1.sp,
                17 to 32.4.sp,
                18 to 30.1.sp,
                19 to 29.sp,
                20 to 27.5.sp
            )

            if (it) {
                emit(32.sp)
            }
            val fontSize = when (length) {

                in 0..12 -> fontSizeMap[12]

                in 13..19 -> fontSizeMap[length]

                else -> fontSizeMap[20]
            }

            emit(fontSize)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            45.4.sp
        )


    val resultFontSize = isLastInputEqual
        .transform {

            if (it && _result.value != ResultType.undefined) {
                emit(45.sp)
            } else {
                emit(35.sp)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            45.sp
        )


    val inputTextColor = isLastInputEqual
        .transform {
            val color = if (it) Color.Gray else Color.White
            emit(color)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Color.White
        )

    val resultTextColor = isLastInputEqual
        .transform {
            val color = if (it) Color.White else Color.Gray
            emit(color)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Color.White
        )

    private fun isResultUndefined() = result.value == ResultType.undefined

    private fun isClean() = _input.value == mutableListOf("0")

    private fun isLastInputAnOperator() = lastElement.value in arrayOf("÷", "x", "-", "+")

    private fun isLastInputPercent() = lastElement.value == ""

    fun onEvent(event: UserEvent) {

        when (event) {
            UserEvent.OnButtonsExpansionButtonClick -> onButtonsExpansionButtonClick()
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

        if (isLastInputEqual.value) {
            _input.value = if (result.value != ResultType.undefined) {
                mutableListOf(result.value, "%")
            } else {
                mutableListOf("0", "%")
            }
            return
        }

        if (lastElement.value.last() == '.') {

            _input.value[input.value.lastIndex] = lastElement.value.dropLast(1)

        }

        _input.value = (_input.value + "%").toMutableList()

        _result.value = calculateResult()
    }

    private fun onDeleteButtonClick() {

        when {

            isLastInputEqual.value -> return

            _input.value.singleOrNull()?.length == 1 -> _input.value = mutableListOf("0")

            lastElement.value.length == 1 -> _input.value.removeAt(_input.value.lastIndex)

            else -> _input.value[_input.value.lastIndex] = lastElement.value.dropLast(1)

        }
        _result.value = calculateResult()
    }

    private fun onOperatorButtonClick(operatorSymbol: String) {

        if (isLastInputEqual.value) {
            _input.value = if (_result.value != ResultType.undefined) {
                mutableListOf(_result.value, operatorSymbol)
            } else {
                mutableListOf("0", operatorSymbol)
            }
            return
        }

        if (lastElement.value.last() == '.') {

            val lastElement = lastElement.value.dropLast(1)
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

            lastElement.value.length == 15 -> return

            isLastInputEqual.value || isClean() -> _input.value = mutableListOf(number.toString())

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
            lastElement.value.length == 15 || "." in lastElement.value -> return

            isLastInputEqual.value -> _input.value = mutableListOf("0.")

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

        if (isLastInputEqual.value) {
            return
        }

        if (lastElement.value.last() == '.') {

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

        if (_input.value.size == 1) {
            return (input.value.single().toDouble() * 1).toString()
        }

        val operatorsSymbol = arrayOf(
            arrayOf("÷", "x", "%"),
            arrayOf("-", "+")
        )

        val newInput =
            _input.value.dropLast(if (isLastInputAnOperator()) 1 else 0).toMutableList()

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