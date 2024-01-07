package com.sadegh.calculator.presentation.main_screen.util

sealed class Operator(val symbol: String) {

    abstract operator fun invoke(number1: Double, number2: Double): Double

    object AddOperator : Operator("+") {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 + number2
        }
    }

    object SubtractOperator : Operator("-") {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 - number2
        }
    }

    object DivisionOperator : Operator("÷") {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 / number2
        }
    }

    object MultiplyOperator : Operator("x") {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 * number2
        }
    }

    object PercentageOperator : Operator("%") {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return (number1 * number2) / 100
        }
    }

    companion object {
        fun fromSymbol(symbol: String): Operator {

            return when (symbol) {

                "+" -> AddOperator

                "-" -> SubtractOperator

                "x" -> MultiplyOperator

                "%" -> PercentageOperator

                else -> DivisionOperator
            }
        }
    }
}