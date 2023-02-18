package com.sadegh.calculator.homeScreen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

class Screen(var input: MutableList<String>, var result: String = "") {

    val formattedInput = input.separateAllThreeDigitsOfNumberWithComma().joinToString("")
        .dropLast(if (isLastInputEqual()) 1 else 0).formatInput()

    val formattedResult = mutableListOf(result).separateAllThreeDigitsOfNumberWithComma().single()
        .formatResult()

    private
    val lastElement: String
        get() = input.last()

    val inputFontSize: TextUnit?
        get() {
            val length = formattedInput.length

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

            if (isLastInputEqual()) {
                return 32.sp
            }
            return when (length) {

                in 0..12 -> fontSizeMap[12]

                in 13..19 -> fontSizeMap[length]

                else -> fontSizeMap[20]
            }

        }

    val resultFontSize = if (isLastInputEqual() && result != ResultType.undefined) {
        45.sp
    } else {
        35.sp
    }

    val inputTextColor = if (isLastInputEqual()) Color.Gray else Color.White

    val resultTextColor = if (isLastInputEqual()) Color.White else Color.Gray

    fun onClickButtonChange(buttonSymbol: String, startIndex: Int, changeStartIndex: (Int) -> Unit) {

        when (buttonSymbol) {

            "C" -> clean()

            "%" -> addPercent()

            "DEL" -> deleteLastInput()

            "÷", "x", "-", "+" -> addOperator(buttonSymbol)

            in "0".."9" -> addDigit(buttonSymbol)

            "." -> addPoint()

            "more" -> onClickMoreButtonChange(startIndex, changeStartIndex)

            "=" -> addEqual()

        }
    }


    private fun clean() {

        input = mutableListOf("0")
        result = ""
    }

    private fun addPercent() {

        if (isLastInputEqual()) {
            input = if (result != ResultType.undefined) {
                mutableListOf(result, "%")
            } else {
                mutableListOf("0", "%")
            }
            return
        }

        if (lastElement.last() == '.') {

            input[input.lastIndex] = lastElement.dropLast(1)

        }

        input += "%"
        result = calculateResult()

    }

    private fun addDigit(digitSymbol: String) {

        when {

            lastElement.length == 15 -> return

            isLastInputEqual() || isClean() -> input = mutableListOf(digitSymbol)

            isLastInputAnOperator() || isLastInputPercent() -> input.add(digitSymbol)

            else -> input[input.lastIndex] = "$lastElement$digitSymbol"
        }

        result = calculateResult()
    }

    private fun addPoint() {

        when {
            lastElement.length == 15 || "." in lastElement -> return

            this.isLastInputEqual() -> input = mutableListOf("0.")

            isLastInputAnOperator() || isLastInputPercent() -> input.add("0.")

            else -> input[input.lastIndex] = "$lastElement."

        }
    }

    private fun deleteLastInput() {

        when {

            isLastInputEqual() -> return

            input.singleOrNull()?.length == 1 -> input = mutableListOf("0")

            lastElement.length == 1 -> input.removeAt(input.lastIndex)

            else -> input[input.lastIndex] = lastElement.dropLast(1)

        }
        result = calculateResult()
    }

    private fun addOperator(buttonSymbol: String) {

        if (isLastInputEqual()) {
            input = if (result != ResultType.undefined) {
                mutableListOf(result, buttonSymbol)
            } else {
                mutableListOf("0", buttonSymbol)
            }
            return
        }

        if (lastElement.last() == '.') {

            input[input.lastIndex] = lastElement.dropLast(1)

        }

        if (isLastInputAnOperator()) {

            input[input.lastIndex] = buttonSymbol
        } else {

            input += buttonSymbol
        }
    }

    private fun onClickMoreButtonChange(startIndex: Int, changeStartIndex: (Int) -> Unit) {

        changeStartIndex((startIndex + 1) % 2)
    }

    private fun addEqual() {

        if (isLastInputEqual()) {
            return
        }

        if (lastElement.last() == '.') {

            input[input.lastIndex] = lastElement.dropLast(1)

        }
        input += "="
    }

    private fun calculateResult(): String {

        if (isResultUndefined()) {
            return result
        }

        if (isClean()) {
            return ""
        }

        if (input.size == 1) {
            return (input.single().toDouble() * 1).toString()
        }

        val operatorsSymbol = arrayOf(
            arrayOf("÷", "x", "%"),
            arrayOf("-", "+")
        )

        val newInput = input.dropLast(if (isLastInputAnOperator()) 1 else 0).toMutableList()

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

    private fun isClean() = input == mutableListOf("0")

    private fun isLastInputEqual() = lastElement == "="

    private fun isLastInputPercent() = lastElement == "%"

    private fun isLastInputAnOperator() = lastElement in arrayOf("÷", "x", "-", "+")

    private fun isResultUndefined() = result == ResultType.undefined

}