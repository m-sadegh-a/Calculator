package com.sadegh.calculator.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calculator.R
import com.sadegh.calculator.homeScreen.Button
import com.sadegh.calculator.homeScreen.Screen
import com.sadegh.calculator.presentation.main_screen.components.Buttons
import com.sadegh.calculator.presentation.main_screen.components.ResultBox
import com.sadegh.calculator.ui.theme.operatorColor

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
            bottomText = screenState.formattedResult,
            bottomTextFontSize = screenState.resultFontSize,
            bottomTextColor = screenState.resultTextColor
        )

        val buttons = arrayOf(
            arrayOf(
                Button(""),
                Button(""),
                Button(""),
                Button(""),
                Button(""),
            ),
            arrayOf(
                Button(""),
                Button(
                    symbolAsString = "C",
                    contentColor = Color.Black,
                    color = Color.Gray,
                ),
                Button(
                    symbolAsString = "%",
                    symbolAsIconId = R.drawable.ic_percent,
                    contentColor = Color.Black,
                    color = Color.Gray
                ),
                Button(
                    symbolAsString = "DEL",
                    symbolAsIconId = R.drawable.ic_backspace,
                    iconSize = 40.dp,
                    contentColor = Color.Black,
                    color = Color.Gray
                ),
                Button(
                    symbolAsString = "÷",
                    symbolAsIconId = R.drawable.ic_divide,
                    iconSize = 35.dp,
                    color = operatorColor
                )
            ),
            arrayOf(
                Button(""),
                Button(symbolAsString = "7"),
                Button(symbolAsString = "8"),
                Button(symbolAsString = "9"),
                Button(
                    symbolAsString = "x",
                    symbolAsIconId = R.drawable.ic_multiply,
                    color = operatorColor
                )
            ),
            arrayOf(
                Button(""),
                Button(symbolAsString = "4"),
                Button(symbolAsString = "5"),
                Button(symbolAsString = "6"),
                Button(
                    symbolAsString = "-",
                    symbolAsIconId = R.drawable.ic_minus,
                    color = operatorColor
                )
            ),
            arrayOf(
                Button(""),
                Button(symbolAsString = "1"),
                Button(symbolAsString = "2"),
                Button(symbolAsString = "3"),
                Button(
                    symbolAsString = "+",
                    symbolAsIconId = R.drawable.ic_add,
                    iconSize = 25.dp,
                    color = operatorColor
                )
            ),
            arrayOf(
                Button(""),
                Button(symbolAsString = "more", symbolAsIconId = R.drawable.ic_show_more_operator),
                Button(symbolAsString = "0"),
                Button(symbolAsString = "."),
                Button(
                    symbolAsString = "=",
                    symbolAsIconId = R.drawable.ic_equal,
                    color = operatorColor
                )
            ),
        )

        Buttons(
            modifier = Modifier.fillMaxSize(),
            screen = screenState,
            buttons = buttons,
        ) {
            screenState = it
        }
    }
}







