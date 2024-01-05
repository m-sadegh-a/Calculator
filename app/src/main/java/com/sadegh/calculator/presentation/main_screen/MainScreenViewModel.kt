package com.sadegh.calculator.presentation.main_screen

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadegh.calculator.presentation.main_screen.util.Operator
import com.sadegh.calculator.presentation.main_screen.util.ResultType
import com.sadegh.calculator.presentation.main_screen.util.formatInput
import com.sadegh.calculator.presentation.main_screen.util.formatResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor() : ViewModel() {

    private val input = MutableStateFlow(listOf("0"))

    val formattedInput = input.transform {

        val formattedInput = formatInput(it)

        emit(formattedInput)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            "0"
        )

    private val result = MutableStateFlow("")
    val formattedResult = result
        .transform {

            val formattedResult = formatResult(it)

            emit(formattedResult)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

    private val _startIndex = MutableStateFlow(1)
    val startIndex = _startIndex.asStateFlow()

    val inputFontSize = input
        .transform {

            val length = it.size

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

            if (it.last() == "=") {
                emit(32.sp)
                return@transform
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


    val resultFontSize = input
        .transform {

            if (it.last() == "=" && result.value != ResultType.UNDEFINED) {
                emit(45.sp)
            } else {
                emit(35.sp)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            35.sp
        )


    val inputTextColor = input
        .transform {
            val color = if (it.last() == "=") Color.Gray else Color.White
            emit(color)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Color.White
        )

    val resultTextColor = input
        .transform {
            val color = if (it.last() == "=") Color.White else Color.Gray
            emit(color)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Color.White
        )

    private val lastElement
        get() = input.value.last()

    private fun isResultUndefined() = formattedResult.value == ResultType.UNDEFINED

    private fun isClean() = input.value == listOf("0")

    private fun isLastInputAnOperator() = lastElement in arrayOf("÷", "x", "-", "+")

    private fun isLastInputPercent() = lastElement == "%"
    private fun isLastInputEqual() = lastElement == "="

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

        input.value = listOf("0")
        result.value = ""
    }

    private fun onPercentButtonClick() {

        if (isLastInputEqual()) {

            input.value = listOf(result.value, "%")

            return
        }

        val lastCharacterInLastInput = lastElement.last()
        if (lastCharacterInLastInput == '.') {

            val newLastInput = lastElement.dropLast(1)
            input.value = input.value.dropLast(1) + newLastInput

        }

        input.value = input.value + "%"

        result.value = calculateResult()
    }

    private fun onDeleteButtonClick() {

        when {

            isLastInputEqual() -> return

            input.value.singleOrNull()?.length == 1 -> input.value = listOf("0")

            lastElement.length == 1 -> input.value = input.value.dropLast(1)

            else -> {
                val newLastInput = lastElement.dropLast(1)
                input.value = input.value.dropLast(1) + newLastInput
            }

        }
        result.value = calculateResult()
    }

    private fun onOperatorButtonClick(operatorSymbol: String) {

        if (isLastInputEqual()) {
            input.value = if (result.value != ResultType.UNDEFINED) {
                listOf(result.value, operatorSymbol)
            } else {
                listOf("0", operatorSymbol)
            }
            return
        }

        if (lastElement.last() == '.') {

            val lastElement = lastElement.dropLast(1)
            input.value = input.value.dropLast(1) + lastElement

        }

        if (isLastInputAnOperator()) {

            input.value = input.value.dropLast(1) + operatorSymbol
        } else {

            input.value = input.value + operatorSymbol
        }
    }

    private fun onNumberButtonClick(number: Int) {

        when {

            lastElement.length == 15 -> return

            isLastInputEqual() || isClean() -> input.value = listOf(number.toString())

            isLastInputAnOperator() || isLastInputPercent() -> {

                input.value = input.value + number.toString()
            }

            else -> {
                val newLastElement = "${lastElement}$number"
                input.value = input.value.dropLast(1) + newLastElement
            }
        }

        result.value = calculateResult()
    }

    private fun onPointButtonClick() {

        when {
            lastElement.length == 15 || "." in lastElement -> return

            isLastInputEqual() -> input.value = listOf("0.")

            isLastInputAnOperator() || isLastInputPercent() -> {
                input.value = input.value + "0."
            }

            else -> {
                val newLastElement = "$lastElement."
                input.value = input.value.dropLast(1) + newLastElement
            }
        }
    }

    private fun onEqualButtonClick() {

        if (isLastInputEqual()) {
            return
        }

        if (lastElement.last() == '.') {

            input.value = input.value.dropLast(1)

        }
        input.value = input.value + "="

    }

    private fun calculateResult(): String {

        if (isResultUndefined()) {
            return formattedResult.value
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

        val newInput =
            input.value.dropLast(if (isLastInputAnOperator()) 1 else 0).toMutableList()

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
                        return ResultType.UNDEFINED
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