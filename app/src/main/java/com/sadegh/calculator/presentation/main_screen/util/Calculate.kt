package com.sadegh.calculator.presentation.main_screen.util

fun calculateResult(inputs: List<String>): String {

    if (inputs == listOf("0")) {
        return ""
    }

    if (inputs.size == 1) {
        return (inputs.single().toDouble() * 1).toString()
    }

    val inputsWithoutLastOperator =
        if (inputs.last() in listOf("÷", "x", "-", "+")) {
            inputs.dropLast(1)
        } else {
            inputs
        }
            .toMutableList()

    val operatorsPriorities = Operator.priorities

    return calculateResult(inputsWithoutLastOperator, operatorsPriorities)

}

fun calculateResult(
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

infix fun MutableList<String>.applyOperatorsWithPriority(priority: Int) {

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

fun MutableList<String>.applyOperator(operator: Operator, operatorIndex: Int) {

    if (operator == Operator.PercentageOperator) {

        //nextInput is null when the percentage is the last element in inputs
        val nextInput = this.getOrNull(operatorIndex + 1)

        /*
        check that there is a number after the percentage
        Example:
        inputs before if branch = 9 % 3
        inputs after if branch = 9 % x 3
         */
        if (nextInput?.toDoubleOrNull() != null) {

            this.add(operatorIndex + 1, "x")

        }

        /*
        add number 1 after percentage operator
        Example: 9 % x 3 -> 9 % 1 x 3
         */
        this.add(operatorIndex + 1, "1")

    }

    val number1 = this[operatorIndex - 1].toDouble()
    val number2 = this[operatorIndex + 1].toDouble()

    val result = operate(number1, number2, operator)

    this[operatorIndex + 1] = result
    this.removeAt(operatorIndex)
    this.removeAt(operatorIndex - 1)
}

fun operate(number1: Double, number2: Double, operator: Operator): String {

    if (number2 == 0.0 && operator == Operator.DivisionOperator) {
        throw ArithmeticException(ResultType.UNDEFINED)
    }

    return operator(number1, number2).toString()

}
