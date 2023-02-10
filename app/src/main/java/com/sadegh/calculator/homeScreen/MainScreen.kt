package com.sadegh.calculator.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen() {

    var inputTextState by remember {
        mutableStateOf(mutableListOf("0"))
    }

    var resultTextState by remember {
        mutableStateOf("0")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF29292B)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val input =
            inputTextState.separateAllThreeDigitsOfNumberWithComma().formatInput()

        val result = mutableListOf(resultTextState.formatResult())
            .separateAllThreeDigitsOfNumberWithComma().single()

        val fontSizeMap = mapOf(
            14 to 45.sp,
            15 to 41.818.sp,
            16 to 39.2.sp,
            17 to 36.8.sp,
            18 to 34.9.sp,
            19 to 33.sp,
            20 to 31.6.sp
        )

        val topTextFontSize = when (input.length) {

            in 0..14 -> fontSizeMap[14]

            in 15..19 -> fontSizeMap[input.length]

            else -> fontSizeMap[19]
        }

        val lastNumberOrOperator = input.last()

        val bottomTextFontSize =
            if (lastNumberOrOperator == '=' && result != "can not divide by zero") {
                45.sp
            } else {
                35.sp
            }

        ResultBox(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.40f),
            topText = if (lastNumberOrOperator == '=') input.dropLast(1) else input,
            topTextFontSize = if (lastNumberOrOperator == '=') 35.sp else topTextFontSize!!,
            topTextColor = if (lastNumberOrOperator == '=') Color.Gray else Color.White,
            bottomText = result,
            bottomTextFontSize = bottomTextFontSize,
            bottomTextColor = if (lastNumberOrOperator == '=') Color.White else Color.Gray,
        )

        val buttonsText = arrayOf(
            arrayOf("AC", "DEL", "/"),
            arrayOf("7", "8", "9", "x"),
            arrayOf("4", "5", "6", "-"),
            arrayOf("1", "2", "3", "+"),
            arrayOf("0", ".", "=")
        )

        Buttons(
            modifier = Modifier.fillMaxSize(),
            buttonsText = buttonsText,
            input = inputTextState,
            updateInput = { inputTextState = it },
            result = resultTextState,
            updateResult = { resultTextState = it },
        )

    }
}






