package com.sadegh.calculator.presentation.main_screen.util

sealed interface Input {

    sealed class OperatorInput(val priority: Int) : Input {
        abstract operator fun invoke(number1: Double, number2: Double): Double

        sealed class BasicOperatorInput(priority: Int) : OperatorInput(priority) {

            object AddOperatorInput : BasicOperatorInput(5) {
                override operator fun invoke(number1: Double, number2: Double): Double {
                    return number1 + number2
                }

                override fun toString() = "+"

            }

            object SubtractOperatorInput : BasicOperatorInput(5) {
                override operator fun invoke(number1: Double, number2: Double): Double {
                    return number1 - number2
                }

                override fun toString() = "-"

            }

            object DivisionOperatorInput : BasicOperatorInput(4) {
                override operator fun invoke(number1: Double, number2: Double): Double {
                    return number1 / number2
                }

                override fun toString() = "÷"

            }

            object MultiplyOperatorInput : BasicOperatorInput(4) {
                override operator fun invoke(number1: Double, number2: Double): Double {
                    return number1 * number2
                }

                override fun toString() = "x"

            }
        }

        object PercentageOperatorInput : OperatorInput(1) {

            override operator fun invoke(number1: Double, number2: Double): Double {
                return (number1 * number2) / 100
            }

            override fun toString() = "%"

        }

        companion object {

            val priorities = listOf(1, 4, 5)

            fun getOperatorFromSymbolOrNull(symbol: String): OperatorInput? {

                return when (symbol) {

                    "+" -> BasicOperatorInput.AddOperatorInput

                    "-" -> BasicOperatorInput.SubtractOperatorInput

                    "x" -> BasicOperatorInput.MultiplyOperatorInput

                    "%" -> PercentageOperatorInput

                    "÷" -> BasicOperatorInput.DivisionOperatorInput

                    else -> null
                }
            }
        }
    }

    open class Number (private val stringValue: String) : Input {
        object NeperNumber : Number("e")

        val doubleValue: Double
            get() = if (stringValue == "e") {
                Math.E
            } else {
                stringValue.toDouble()
            }

        override fun toString() = stringValue

    }

    object Equal : Input {
        override fun toString() = "="

    }
}
