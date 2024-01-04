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

        val buttons = buttons

        Buttons(
            modifier = Modifier.fillMaxSize(),
            screen = screenState,
            buttons = buttons,
        ) {
            screenState = it
        }
    }
}







