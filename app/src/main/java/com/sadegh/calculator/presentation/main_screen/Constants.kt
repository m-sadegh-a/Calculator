package com.sadegh.calculator.presentation.main_screen

import com.example.calculator.R
import com.sadegh.calculator.homeScreen.Button

val buttons = listOf(
    listOf(
        Button.Undefined,
        Button.Undefined,
        Button.Undefined,
        Button.Undefined,
        Button.Undefined,
    ),
    listOf(
        Button.Undefined,
        Button.Clear,
        Button.Percent,
        Button.Delete,
        Button.Operator("÷", R.drawable.ic_divide)
    ),
    listOf(
        Button.Undefined,
        Button.Number(7),
        Button.Number(8),
        Button.Number(9),
        Button.Operator("x", R.drawable.ic_multiply)
    ),
    listOf(
        Button.Undefined,
        Button.Number(4),
        Button.Number(5),
        Button.Number(6),
        Button.Operator("-", R.drawable.ic_minus)
    ),
    listOf(
        Button.Undefined,
        Button.Number(1),
        Button.Number(2),
        Button.Number(3),
        Button.Operator("+", R.drawable.ic_add)
    ),
    listOf(
        Button.Undefined,
        Button.Expansion,
        Button.Number(0),
        Button.Point,
        Button.Equal
    ),
)