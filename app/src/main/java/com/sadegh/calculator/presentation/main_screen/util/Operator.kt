package com.sadegh.calculator.presentation.main_screen.util

class Operator(private val symbol: String) {

    companion object {

        private val operatorMap = mapOf(

            "÷" to { number1: Double, number2: Double -> number1 / number2 },
            "x" to { number1: Double, number2: Double -> number1 * number2 },
            "-" to { number1: Double, number2: Double -> number1 - number2 },
            "+" to { number1: Double, number2: Double -> number1 + number2 },
            "%" to { number1: Double, number2: Double -> (number1 * number2) / 100 },
        )
    }

    fun operate(number1: Double, number2: Double) =
        operatorMap[symbol]?.let { it(number1, number2) }
}