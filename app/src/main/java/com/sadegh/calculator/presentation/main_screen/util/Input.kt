package com.sadegh.calculator.presentation.main_screen.util

sealed class Input(private val displayValue: String) {

    sealed class OperatorInput(val priority: Int, symbol: String) : Input(symbol) {

        abstract operator fun invoke(number1: Double, number2: Double): Double

        sealed class BasicOperatorInput(priority: Int, symbol: String) :
            OperatorInput(priority, symbol) {

            object AddOperatorInput : BasicOperatorInput(5, "+") {
                override operator fun invoke(number1: Double, number2: Double): Double {
                    return number1 + number2
                }
            }

            object SubtractOperatorInput : BasicOperatorInput(5, "-") {
                override operator fun invoke(number1: Double, number2: Double): Double {
                    return number1 - number2
                }
            }

            object DivisionOperatorInput : BasicOperatorInput(4, "÷") {
                override operator fun invoke(number1: Double, number2: Double): Double {
                    return number1 / number2
                }
            }

            object MultiplyOperatorInput : BasicOperatorInput(4, "x") {
                override operator fun invoke(number1: Double, number2: Double): Double {
                    return number1 * number2
                }
            }
        }

        object PercentageOperatorInput : OperatorInput(1, "%") {

            override operator fun invoke(number1: Double, number2: Double): Double {
                return (number1 * number2) / 100
            }
        }

        companion object {

            val priorities = listOf(1, 4, 5)
        }
    }

    open class Number(stringValue: String) : Input(stringValue) {
        object NeperNumber : Number("e")

        val doubleValue: Double
            get() = if (toString() == "e") {
                Math.E
            } else {
                toString().toDouble()
            }

    }

    object Equal : Input("=")

    override fun toString() = displayValue
}
