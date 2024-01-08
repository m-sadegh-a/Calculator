package com.sadegh.calculator.presentation.main_screen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadegh.calculator.presentation.main_screen.util.ResultType
import com.sadegh.calculator.presentation.main_screen.util.calculateResult
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

    private var lastUserEvent: UserEvent? = null

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

    private val result = input.transform {

        val isLastInputABasicOperator = isLastInputABasicOperator()
        val isLastUseEventNotOnBackspaceClick = lastUserEvent !is UserEvent.OnBackspaceButtonClick

        if (isLastInputABasicOperator && isLastUseEventNotOnBackspaceClick) {

            return@transform

        }

        val result = calculateResult(it)
        emit(result)

    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

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

    private val _isExpand = MutableStateFlow(false)
    val isExpand = _isExpand.asStateFlow()

    val inputFontSize = formattedInput
        .transform {

            val length = it.length

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

            if (it.last() == '=') {
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

    private fun isClean() = input.value == listOf("0")

    private fun isLastInputABasicOperator() = lastElement in arrayOf("÷", "x", "-", "+")

    private fun isLastInputPercent() = lastElement == "%"
    private fun isLastInputEqual() = lastElement == "="

    fun onEvent(event: UserEvent) {

        lastUserEvent = event

        when (event) {
            UserEvent.OnButtonsExpansionButtonClick -> onButtonsExpansionButtonClick()
            UserEvent.OnClearButtonClick -> onClearButtonClick()
            UserEvent.OnBackspaceButtonClick -> onBackspaceButtonClick()
            UserEvent.OnEqualButtonClick -> onEqualButtonClick()
            UserEvent.OnPointButtonClick -> onPointButtonClick()
            is UserEvent.OnDigitButtonClick -> onNumberButtonClick(event.number)
            is UserEvent.OnOperatorButtonClick -> onOperatorButtonClick(event.symbol)
        }
    }

    private fun onButtonsExpansionButtonClick() {

        _isExpand.value = !_isExpand.value
    }

    private fun onClearButtonClick() {

        input.value = listOf("0")
    }

    private fun onBackspaceButtonClick() {

        when {

            isLastInputEqual() -> return

            input.value.singleOrNull()?.length == 1 -> input.value = listOf("0")

            lastElement.length == 1 -> input.value = input.value.dropLast(1)

            else -> {
                val newLastInput = lastElement.dropLast(1)
                input.value = input.value.dropLast(1) + newLastInput
            }

        }
    }

    private fun onOperatorButtonClick(operatorSymbol: String) {

        when {

            operatorSymbol == lastElement -> return

            isLastInputEqual() -> {

                input.value = if (result.value != ResultType.UNDEFINED) {
                    listOf(result.value, operatorSymbol)
                } else {
                    listOf("0", operatorSymbol)
                }
            }

            isLastInputABasicOperator() -> {
                input.value = input.value.dropLast(1) + operatorSymbol
            }

            //last input is percentage or a number
            else -> {

                if (lastElement.last() == '.') {

                    val lastElement = lastElement.dropLast(1)
                    input.value = input.value.dropLast(1) + lastElement
                }

                input.value = input.value + operatorSymbol
            }
        }
    }

    private fun onNumberButtonClick(number: Int) {

        when {

            lastElement.length == 15 -> return

            isLastInputEqual() || isClean() -> input.value = listOf(number.toString())

            isLastInputABasicOperator() || isLastInputPercent() -> {

                input.value = input.value + number.toString()
            }

            else -> {
                val newLastElement = "${lastElement}$number"
                input.value = input.value.dropLast(1) + newLastElement
            }
        }
    }

    private fun onPointButtonClick() {

        when {
            lastElement.length == 15 || "." in lastElement -> return

            isLastInputEqual() -> input.value = listOf("0.")

            isLastInputABasicOperator() || isLastInputPercent() -> {
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
}