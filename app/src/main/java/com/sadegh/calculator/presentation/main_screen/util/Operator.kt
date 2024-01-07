package com.sadegh.calculator.presentation.main_screen.util

sealed class Operator(val symbol: String, val priority: Int) {

    abstract operator fun invoke(number1: Double, number2: Double): Double

    object AddOperator : Operator("+", 5) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 + number2
        }
    }

    object SubtractOperator : Operator("-", 5) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 - number2
        }
    }

    object DivisionOperator : Operator("÷", 4) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 / number2
        }
    }

    object MultiplyOperator : Operator("x", 4) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 * number2
        }
    }

    object PercentageOperator : Operator("%", 4) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return (number1 * number2) / 100
        }
    }

    companion object {

        val priorities = listOf(4, 5)

        val symbols = listOf("x", "÷", "%", "+", "-")

        fun getOperatorFromSymbolOrNull(symbol: String): Operator? {

            return when (symbol) {

                "+" -> AddOperator

                "-" -> SubtractOperator

                "x" -> MultiplyOperator

                "%" -> PercentageOperator

                "÷" -> DivisionOperator

                else -> null
            }
        }
    }
}