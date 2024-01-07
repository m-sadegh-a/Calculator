package com.sadegh.calculator.presentation.main_screen.util

fun calculateResult(input: List<String>): String {

    if (input == listOf("0")) {
        return ""
    }

    if (input.size == 1) {
        return (input.single().toDouble() * 1).toString()
    }

    val operatorsSymbol = listOf(
        listOf(Operator.MultiplyOperator, Operator.DivisionOperator, Operator.PercentageOperator),
        listOf(Operator.AddOperator, Operator.SubtractOperator)
    )

    val inputWithoutLastOperator =
        if (input.last() in listOf("÷", "x", "-", "+", "%")) {
            input.dropLast(1)
        } else {
            input
        }
            .toMutableList()

    operatorsSymbol.forEach { operatorsWithSamePrecedence ->

        var index = 0
        while (index < inputWithoutLastOperator.size) {

            val numberOrOperator = inputWithoutLastOperator[index]

            if (
                numberOrOperator == operatorsWithSamePrecedence[0].symbol ||
                numberOrOperator == operatorsWithSamePrecedence[1].symbol ||
                numberOrOperator == operatorsWithSamePrecedence.getOrNull(2)?.symbol
            ) {

                val number1 = inputWithoutLastOperator[index - 1].toDouble()
                var number2 = inputWithoutLastOperator.getOrNull(index + 1)?.toDoubleOrNull()

                if (numberOrOperator == "%" && number2 == null) {
                    inputWithoutLastOperator.add(index = index + 1, "1")
                    number2 = 1.0
                }

                if (number2 == 0.0 && numberOrOperator == "÷") {
                    return ResultType.UNDEFINED
                }

                val operator = Operator.getOperatorFromSymbolOrNull(numberOrOperator)
                operator?.let {

                    val result = operator(number1, number2!!).toString()

                    inputWithoutLastOperator[index + 1] = result

                    inputWithoutLastOperator.removeAt(index)

                    inputWithoutLastOperator.removeAt(index - 1)
                }
            } else {

                index++

            }
        }
    }

    return inputWithoutLastOperator.single()
}