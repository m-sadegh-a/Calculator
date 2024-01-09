package com.sadegh.calculator.presentation.main_screen.util

fun calculateResult(inputs: List<Input>): String {

    if (inputs.isEmpty()) {
        return ""
    }

    if (inputs.size == 1) {
        return (inputs.single() as Input.Number).doubleValue.toString()
    }

    val formattedInputs = inputs.toMutableList().format()

    val operatorsPriorities = Input.OperatorInput.priorities

    return calculateResult(formattedInputs, operatorsPriorities)

}

private fun MutableList<Input>.format(): MutableList<Input> {

    if (this.last() is Input.OperatorInput.BasicOperatorInput) {
        this.removeLast()
    }

    val inputsWithoutLastOperator = this

    var index = 0

    while (index <= inputsWithoutLastOperator.lastIndex) {

        when (inputsWithoutLastOperator[index]) {

            is Input.Number.NeperNumber -> {
                index = inputsWithoutLastOperator
                    .changeInputsWhenInputIsNeperNumberAndGetNewIndexValue(index)
            }

            is Input.OperatorInput.PercentageOperatorInput -> {
                inputsWithoutLastOperator.changeInputsWhenInputIsPercentageOperator(index)
            }

            else -> Unit

        }

        index++

    }

    return inputsWithoutLastOperator

}

private fun calculateResult(
    inputs: MutableList<Input>,
    operatorsPriorities: List<Int>,
): String {

    try {

        operatorsPriorities.forEach { operatorPriority ->

            inputs applyOperatorsWithPriority operatorPriority

        }

    } catch (e: ArithmeticException) {
        return e.message!!
    }

    return inputs.single().toString()

}

private infix fun MutableList<Input>.applyOperatorsWithPriority(priority: Int) {

    var index = 0

    while (index <= this.lastIndex) {

        val input = this[index]

        if (input is Input.OperatorInput && input.priority == priority) {

            this.applyOperator(input, index)

            index--
        }

        index++
    }
}

private fun MutableList<Input>.applyOperator(operator: Input.OperatorInput, operatorIndex: Int) {

    val number1 = (this[operatorIndex - 1] as Input.Number).doubleValue
    val number2 = (this[operatorIndex + 1] as Input.Number).doubleValue

    val result = operate(number1, number2, operator)

    this[operatorIndex + 1] = result
    this.removeAt(operatorIndex)
    this.removeAt(operatorIndex - 1)
}

private fun MutableList<Input>.changeInputsWhenInputIsPercentageOperator(percentOperatorIndex: Int) {

    //nextInput is null when the percentage is the last element in inputs
    val nextInput = this.getOrNull(percentOperatorIndex + 1)

    /*
    check that there is a number after the percentage
    Example:
    inputs before if branch = 9 % 3
    inputs after if branch = 9 % x 3
     */
    if (nextInput is Input.Number) {

        this.add(
            percentOperatorIndex + 1,
            Input.OperatorInput.BasicOperatorInput.MultiplyOperatorInput
        )

    }

    /*
    add number 1 after percentage operator
    Example: 9 % x 3 -> 9 % 1 x 3
     */
    this.add(percentOperatorIndex + 1, Input.Number("1"))
}

private fun MutableList<Input>.changeInputsWhenInputIsNeperNumberAndGetNewIndexValue(
    neperIndex: Int
): Int {

    val beforeInput = this.getOrNull(neperIndex - 1)
    val nextInput = this.getOrNull(neperIndex + 1)

    if (nextInput is Input.Number) {

        this.add(neperIndex + 1, Input.OperatorInput.BasicOperatorInput.MultiplyOperatorInput)
    }

    if (beforeInput is Input.Number) {

        this.add(neperIndex, Input.OperatorInput.BasicOperatorInput.MultiplyOperatorInput)

        return neperIndex + 1

    }

    return neperIndex
}

private fun operate(number1: Double, number2: Double, operator: Input.OperatorInput): Input {

    if (number2 == 0.0 && operator == Input.OperatorInput.BasicOperatorInput.DivisionOperatorInput) {
        throw ArithmeticException(ResultType.UNDEFINED)
    }

    val inputAsDouble = operator(number1, number2)

    return Input.Number(inputAsDouble.toString())

}
