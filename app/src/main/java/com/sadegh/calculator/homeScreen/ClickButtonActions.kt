package com.sadegh.calculator.homeScreen


fun onClickChange(
    input: MutableList<String>,
    buttonText: String,
    updateInput: (MutableList<String>) -> Unit,
    result: String,
    updateResult: (String) -> Unit,
) {

    when (buttonText) {

        "AC" -> onClickACButtonChange(updateInput, updateResult)

        in "0".."9" -> {
            onClickNumbersButtonChange(input, buttonText, updateInput, updateResult)
        }

        "." -> onClickPointButtonChange(input, updateInput, updateResult)

        "DEL" -> {
            onClickDeleteButtonChange(input, updateInput, updateResult)

        }

        "/", "x", "-", "+" -> onClickOperatorsButtonChange(input, buttonText, result, updateInput)

        "=" -> onClickEqualButtonChange(input, updateInput)

    }
}

fun onClickACButtonChange(
    updateInput: (MutableList<String>) -> Unit,
    updateResult: (String) -> Unit
) {

    updateInput(mutableListOf("0"))
    updateResult("0")

}

fun onClickNumbersButtonChange(
    numbersAndOperators: MutableList<String>,
    buttonText: String,
    updateInput: (MutableList<String>) -> Unit,
    updateResult: (String) -> Unit
) {

    if (numbersAndOperators.last().length == 15) {
        return
    }


    if (numbersAndOperators.last() == "=" || numbersAndOperators.isZero()) {

        updateInput(mutableListOf(buttonText))
        updateResult(buttonText)
        return

    }

    val input = numbersAndOperators.take(numbersAndOperators.size).toMutableList()

    if (input.isLastElementAnOperator) {

        input.add(buttonText)

    } else {

        val lastNumber = numbersAndOperators.last()

        input[input.lastIndex] = lastNumber + buttonText
    }

    updateInput(input)
    updateResult(input.calculateResult())
}

fun onClickPointButtonChange(
    numbersAndOperators: MutableList<String>,
    updateInput: (MutableList<String>) -> Unit,
    updateResult: (String) -> Unit
) {

    if (numbersAndOperators.last().length == 15) {
        return
    }

    when {

        "." in numbersAndOperators.last() -> return

        numbersAndOperators.last() == "=" -> {
            updateInput(mutableListOf("0."))
            updateResult("0.")
            return
        }

        numbersAndOperators.isLastElementAnOperator -> {

            numbersAndOperators.add("0.")
            updateInput(numbersAndOperators)
            updateResult("0")
            return
        }

        else -> {
            val lastNumber = numbersAndOperators.last()
            val lastNumberAfterClickingPointButton = "$lastNumber."

            val input = numbersAndOperators.take(numbersAndOperators.size - 1).toMutableList()
            input.add(lastNumberAfterClickingPointButton)
            updateInput(input)

        }
    }
}

fun onClickDeleteButtonChange(
    numbersAndOperators: MutableList<String>,
    updateInput: (MutableList<String>) -> Unit,
    updateResult: (String) -> Unit
) {

    if (numbersAndOperators.last() == "=") {
        return
    }

    if (numbersAndOperators.size == 1 && numbersAndOperators.single().length == 1) {
        updateInput(mutableListOf("0"))
        updateResult("0")
        return

    }

    val input = numbersAndOperators.deleteLastCharacter()

    updateInput(input)
    updateResult(input.calculateResult())

}

fun onClickOperatorsButtonChange(
    numbersAndOperators: MutableList<String>,
    buttonText: String,
    result: String,
    updateInput: (MutableList<String>) -> Unit,
) {

    if (numbersAndOperators.last() == "=") {
        if (result != "can not divide by zero") {
            updateInput(mutableListOf(result, buttonText))
        } else {
            updateInput(mutableListOf("0", buttonText))
        }
        return
    }

    val input = numbersAndOperators.take(numbersAndOperators.size).toMutableList()

    if (numbersAndOperators.last() == "0.") {

        input[input.lastIndex] = "0"

    }

    if (numbersAndOperators.isLastElementAnOperator) {

        input[numbersAndOperators.lastIndex] = buttonText
    } else {

        input += buttonText
    }
    updateInput(input)

}

fun onClickEqualButtonChange(
    numbersAndOperators: MutableList<String>,
    updateInput: (MutableList<String>) -> Unit,
) {

    if (numbersAndOperators.isLastCharacterEqualToCurrentCharacter('=')) {
        return
    }

    val input = numbersAndOperators.take(numbersAndOperators.size).toMutableList()

    if (numbersAndOperators.last() == "0.") {

        input[input.lastIndex] = "0"

    }

    input += "="
    updateInput(input)

}

private fun MutableList<String>.deleteLastCharacter(): MutableList<String> {

    val input = this.take(this.size).toMutableList()

    if (input.last().length == 1) {

        input.removeAt(this.lastIndex)
    } else {

        val lastNumber = input.last()

        val lastNumberAfterDeleting = lastNumber.dropLast(1)

        input[input.lastIndex] = lastNumberAfterDeleting
    }
    return input
}

fun List<String>.isZero() = this.size == 1 && this.single() == "0"

val MutableList<String>.isLastElementAnOperator
    get() = this.last() in arrayOf("/", "x", "-", "+")


fun MutableList<String>.isLastCharacterEqualToCurrentCharacter(character: Char): Boolean {

    val lastElement = this.last()

    val lastCharacterInLastElement = lastElement.last()

    return lastCharacterInLastElement == character

}
