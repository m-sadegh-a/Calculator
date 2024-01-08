package com.sadegh.calculator.presentation.main_screen.util

import java.text.DecimalFormat

fun formatInput(input: List<String>): String {

    val inputAsStringAfterFirstFormat = separateAllThreeDigitsOfAllNumbersWithComma(input)
        .joinToString("")
        .dropLast(if (input.last() == "=") 1 else 0)

    return formatStringInput(inputAsStringAfterFirstFormat, 20)
}

/**
 * This function allows only the @param maximumCharacterCountPerLine value
 * characters in each line.
 * Of course, there can be a maximum of maximumCharacterCountPerLine-1 character in the first line
 * if all the digits of a number do not fit in one line,
 * it moves that number along with the operator before it to the next line.
 */

fun formatStringInput(
    stringInput: String,
    maximumCharacterCountPerLine: Int
): String {

    var lastBackSlashIndex = -1
    var lastOperatorIndex = -1
    var inputAsString = stringInput
    var index = 0
    while (index < inputAsString.length) {

        if (inputAsString[index] in arrayOf('÷', 'x', '-', '+', '=', '%')) {
            lastOperatorIndex = index
        }

        val characterCountPerLine = index - lastBackSlashIndex
        if (characterCountPerLine == maximumCharacterCountPerLine) {

            when (inputAsString.getOrNull(index + 1)) {

                /* it checks that if the number of characters in the line is 20
                   and the next character is a digit,
                   it transfers the number along with the operator before it to the next line.
                */
                in '0'..'9', ',', '.' -> {

                    inputAsString = addNewLineBetweenString(inputAsString, lastOperatorIndex)
                    lastBackSlashIndex = lastOperatorIndex

                }

                in arrayOf('÷', 'x', '-', '+', '=', '%') -> {

                    inputAsString = addNewLineBetweenString(inputAsString, index + 1)
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

fun addNewLineBetweenString(input: String, newLineIndex: Int) = buildString {

    append(input.take(newLineIndex))
    append("\n")
    append(input.drop(newLineIndex))
}

fun formatResult(result: String): String {

    if (result == ResultType.UNDEFINED || result.isEmpty()) {
        return result
    }

    val resultAfterFirstFormat = separateAllThreeDigitsOfNumberWithComma(result)

    return format(resultAfterFirstFormat, 8)

}

/**
 * This function does not allow more digits
 * than @param maximumCountOfDigitsAfterPoint between point and E
 * Example : change 9.999999999E9 to 9.9999999E9 with maximumCountOfDigitsAfterPoint=7
 */
fun format(number: String, maximumCountOfDigitsAfterPoint: Int): String {

    if ("E" !in number) {
        return number
    }

    val pointIndex = number.indexOf(".")
    val indexOfE = number.indexOf("E")
    val eAndPowerOfTen = number.replaceBefore("E", "")//example : E8
    val countOfDigitsAfterPoint = indexOfE - pointIndex - 1
    if (countOfDigitsAfterPoint <= maximumCountOfDigitsAfterPoint) {
        return number
    }

    return number.take(pointIndex + maximumCountOfDigitsAfterPoint + 1) + eAndPowerOfTen
}

/**
 * This function separates all three digits from all the numbers in the input with comma
 */
fun separateAllThreeDigitsOfAllNumbersWithComma(input: List<String>): List<String> {

    return input.map { numberOrOperator ->

        if (numberOrOperator in arrayOf("÷", "x", "-", "+", '=', '%')) {
            return@map numberOrOperator
        }

        return@map separateAllThreeDigitsOfNumberWithComma(numberOrOperator)

    }
}

/**
 * This function separates all three digits of the number with comma
 */
fun separateAllThreeDigitsOfNumberWithComma(number: String): String {

    if (number.toDouble() in -1.0..0.0) {
        return number
    }

    val pointIndex = number.indexOf('.')

    val (beforePoint, afterPoint) = if (pointIndex == -1) {
        number to ""
    } else {
        val beforePoint = number.takeWhile { it != '.' }
        val afterPoint = number.takeLastWhile { it != '.' }
        beforePoint to ".$afterPoint"
    }

    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(beforePoint.toLong()) + afterPoint

}
