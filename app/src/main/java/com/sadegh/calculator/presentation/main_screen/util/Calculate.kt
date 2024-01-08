package com.sadegh.calculator.presentation.main_screen.util

fun calculateResult(inputs: List<String>): String {

    if (inputs == listOf("0")) {
        return ""
    }

    if (inputs.size == 1) {
        return if (inputs.single() == "e") {
            Math.E.toString()
        } else {

            (inputs.single().toDouble() * 1).toString()
        }
    }

    val formattedInputs = inputs.toMutableList().format()

    val operatorsPriorities = Operator.priorities

    return calculateResult(formattedInputs, operatorsPriorities)

}

private fun MutableList<String>.format(): MutableList<String> {

    if (this.last() in listOf("÷", "x", "-", "+")) {
        this.removeLast()
    }

    val inputsWithoutLastOperator = this

    var index = 0

    while (index <= inputsWithoutLastOperator.lastIndex) {

        when (inputsWithoutLastOperator[index]) {

            "e" -> {
                index = inputsWithoutLastOperator
                    .changeInputsWhenInputIsNeperNumberAndGetNewIndexValue(index)
            }

            "%" -> inputsWithoutLastOperator.changeInputsWhenInputIsPercentageOperator(index)

        }

        index++

    }

    return inputsWithoutLastOperator

}

private fun calculateResult(
    inputs: MutableList<String>,
    operatorsPriorities: List<Int>,
): String {

    try {

        operatorsPriorities.forEach { operatorPriority ->

            inputs applyOperatorsWithPriority operatorPriority

        }

    } catch (e: ArithmeticException) {
        return e.message!!
    }

    return inputs.single()

}

private infix fun MutableList<String>.applyOperatorsWithPriority(priority: Int) {

    var index = 0

    while (index <= this.lastIndex) {

        val input = this[index]

        //operator is null when it is not equal to an operator in Operator class
        val operator = Operator.getOperatorFromSymbolOrNull(input)

        if (operator != null && operator.priority == priority) {

            this.applyOperator(operator, index)

            index--
        }

        index++
    }
}

private fun MutableList<String>.applyOperator(operator: Operator, operatorIndex: Int) {

    val nextInput = this[operatorIndex + 1]
    val beforeInput = this[operatorIndex - 1]

    val number1 = if (beforeInput == "e") Math.E else beforeInput.toDouble()
    val number2 = if (nextInput == "e") Math.E else nextInput.toDouble()

    val result = operate(number1, number2, operator)

    this[operatorIndex + 1] = result
    this.removeAt(operatorIndex)
    this.removeAt(operatorIndex - 1)
}

private fun MutableList<String>.changeInputsWhenInputIsPercentageOperator(percentOperatorIndex: Int) {

    //nextInput is null when the percentage is the last element in inputs
    val nextInput = this.getOrNull(percentOperatorIndex + 1)

    /*
    check that there is a number after the percentage
    Example:
    inputs before if branch = 9 % 3
    inputs after if branch = 9 % x 3
     */
    if (nextInput?.toDoubleOrNull() != null) {

        this.add(percentOperatorIndex + 1, "x")

    }

    /*
    add number 1 after percentage operator
    Example: 9 % x 3 -> 9 % 1 x 3
     */
    this.add(percentOperatorIndex + 1, "1")
}

private fun MutableList<String>.changeInputsWhenInputIsNeperNumberAndGetNewIndexValue(
    neperIndex: Int
): Int {

    val nextInput = this.getOrNull(neperIndex + 1)

    if (nextInput?.toDoubleOrNull() != null) {

        this.add(neperIndex + 1, "x")
    }

    val beforeInput = this.getOrNull(neperIndex - 1)
    if (beforeInput?.toDoubleOrNull() != null) {

        this.add(neperIndex, "x")

        return neperIndex + 1

    }

    return neperIndex
}

private fun operate(number1: Double, number2: Double, operator: Operator): String {

    if (number2 == 0.0 && operator == Operator.DivisionOperator) {
        throw ArithmeticException(ResultType.UNDEFINED)
    }

    return operator(number1, number2).toString()

}
