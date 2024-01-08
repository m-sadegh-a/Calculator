package com.sadegh.calculator.presentation.main_screen.util

sealed class Operator(val priority: Int) {

    abstract operator fun invoke(number1: Double, number2: Double): Double

    object AddOperator : Operator( 5) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 + number2
        }
    }

    object SubtractOperator : Operator( 5) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 - number2
        }
    }

    object DivisionOperator : Operator( 4) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 / number2
        }
    }

    object MultiplyOperator : Operator( 4) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return number1 * number2
        }
    }

    object PercentageOperator : Operator( 1) {

        override operator fun invoke(number1: Double, number2: Double): Double {
            return (number1 * number2) / 100
        }
    }

    companion object {

        val priorities = listOf(1, 4, 5)

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