package com.sadegh.calculator.homeScreen

import android.util.Log
import kotlin.math.min

fun List<String>.separateAllThreeDigitsOfNumberWithComma(): List<String> {

    if (this.singleOrNull() == ResultType.undefined) {
        return this
    }

    return this.map { numberOrOperator ->

        if (numberOrOperator in arrayOf("/", "x", "-", "+", '=')) {
            return@map numberOrOperator
        }

        val pointIndex = numberOrOperator.indexOf(".")
        val integerPartLength = if (pointIndex != -1) pointIndex else numberOrOperator.length
        val integerPart = numberOrOperator.take(integerPartLength)
        val decimalPart = numberOrOperator.drop(integerPartLength)

        buildString {

            for ((digitIndex,digit) in integerPart.withIndex()) {

                if(digit=='-'){
                    append('-')
                    continue
                }
                append(digit)

                val integerPartOfNumberLastIndex = integerPart.lastIndex
                if ((integerPartOfNumberLastIndex - digitIndex) % 3 == 0 &&
                    digitIndex != integerPartOfNumberLastIndex
                ) {
                    append(',')
                }
            }
            append(decimalPart)
        }
    }
}

/**
 *This function only allows 20 characters per line.
 *of course, 19 characters must be placed in the first line
 *if all the digits of a number do not fit in one line,
 *it moves that number along with the operator before it to the next line.
 */

fun String.formatInput(): String {

    var lastBackSlashIndex = -1
    var lastOperatorIndex = -1
    var inputAsString = this
    var index = 0
    while (index < inputAsString.length) {

        if (inputAsString[index] in arrayOf('/', 'x', '-', '+', '=')) {
            lastOperatorIndex = index
        }

        val maximumCharacterCountPerLine = index - lastBackSlashIndex

        if (maximumCharacterCountPerLine == 20) {

            when (inputAsString.getOrNull(index + 1)) {

                /* it checks that if the number of characters in the line is 20
                   and the next character is a digit,
                   it transfers the number along with the operator before it to the next line.
                */
                in '0'..'9', ',', '.' -> {

                    inputAsString = inputAsString.addNewLineToString(lastOperatorIndex)
                    lastBackSlashIndex = lastOperatorIndex

                }

                in arrayOf('/', 'x', '-', '+', '=') -> {

                    inputAsString = inputAsString.addNewLineToString(index + 1)
                    lastBackSlashIndex = index + 1

                }
            }
            lastOperatorIndex++
            index++
        }
        index++
    }

    return inputAsString
}

fun String.formatResult(): String {

    if ("E" !in this) {
        return this
    }

    val pointIndex = this.indexOf(".")
    val indexOfE = this.indexOf("E")
    val powerOfTen = this.replaceBefore("E", "")
    val countOfDigitsAfterPoint = min(indexOfE - pointIndex - 1, 8)
    val number = this.take(pointIndex + countOfDigitsAfterPoint + 1)
    return number + powerOfTen
}

fun String.addNewLineToString(newLineIndex: Int) = buildString {

    append(this@addNewLineToString.take(newLineIndex))
    append("\n")
    append(this@addNewLineToString.drop(newLineIndex))
}