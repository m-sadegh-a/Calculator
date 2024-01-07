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

            if (operator == Operator.PercentageOperator) {

                //nextInput is null when the percentage is the last element in inputs
                val nextInput = this.getOrNull(index + 1)

                /*
                check if there is another operator immediately after the percentage
                or if the percentage is the last input
                 */
                if (nextInput == null || nextInput in Operator.symbols) {
                    this.add(index + 1, "1")
                }
            }

            val number1 = this[index - 1].toDouble()
            val number2 = this[index + 1].toDouble()

            val result = operate(number1, number2, operator)

            this[index + 1] = result
            this.removeAt(index)
            this.removeAt(index - 1)

            index--
        }

        index++
    }
}

fun operate(number1: Double, number2: Double, operator: Operator): String {

    if (number2 == 0.0 && operator == Operator.DivisionOperator) {
        throw ArithmeticException(ResultType.UNDEFINED)
    }

    return operator(number1, number2).toString()

}
