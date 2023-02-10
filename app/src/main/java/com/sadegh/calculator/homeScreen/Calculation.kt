package com.sadegh.calculator.homeScreen


fun MutableList<String>.calculateResult(): String {

    if (this.size == 1) {
        return (this.single().toDouble() * 1).toString()
    }

    if ("can not divide by zero" in this) {
        return "can not divide by zero"
    }

    val operatorsSymbolWithFunction = mapOf(

        "/" to { number1: String, number2: String -> number1.toDouble() / number2.toDouble() },
        "x" to { number1: String, number2: String -> number1.toDouble() * number2.toDouble() },
        "-" to { number1: String, number2: String -> number1.toDouble() - number2.toDouble() },
        "+" to { number1: String, number2: String -> number1.toDouble() + number2.toDouble() },
    )

    val operatorsSymbol = arrayOf(arrayOf("/", "x"), arrayOf("-", "+"))

    var result: String

    val input = if (this.isLastElementAnOperator) {

        this.take(this.size - 1).toMutableList()
    } else {
        this.take(this.size).toMutableList()

    }
    repeat(2) {

        var index = 0
        while (index < input.size) {

            val numberOrOperator = input[index]

            if (
                numberOrOperator == operatorsSymbol[it][0] ||
                numberOrOperator == operatorsSymbol[it][1]
            ) {

                val number1 = input[index - 1]
                val number2 = input[index + 1]

                if (number2.toDouble() == 0.0 && numberOrOperator == "/") {
                    return "can not divide by zero"

                }
                result = operatorsSymbolWithFunction[numberOrOperator]!!(
                    number1,
                    number2
                ).toString()

                input[index + 1] = result
                input.removeAt(index)
                input.removeAt(index - 1)

            } else {

                index++

            }
        }
    }

    return input.single()
}


