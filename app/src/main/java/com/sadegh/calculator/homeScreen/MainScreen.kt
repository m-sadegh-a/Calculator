package com.sadegh.calculator.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen() {

    var screenState by remember {
        mutableStateOf(Screen(mutableListOf("0")))
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF29292B)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ResultBox(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.40f),
            topText = screenState.formattedInput,
            topTextFontSize = screenState.inputFontSize!!,
            topTextColor = screenState.inputTextColor,
            bottomText =  screenState.formattedResult,
            bottomTextFontSize = screenState.resultFontSize,
            bottomTextColor = screenState.resultTextColor
        )

        val buttonsText = arrayOf(
            arrayOf("AC", "DEL", "/"),
            arrayOf("7", "8", "9", "x"),
            arrayOf("4", "5", "6", "-"),
            arrayOf("1", "2", "3", "+"),
            arrayOf("0", ".", "=")
        )

        val buttons = Array(5) { row ->
            Array(buttonsText[row].size) { column ->
                Button(buttonsText[row][column])
            }
        }

        Buttons(
            modifier = Modifier.fillMaxSize(),
            screen = screenState,
            buttons = buttons,
        ) {
            screenState = it
        }
    }
}







