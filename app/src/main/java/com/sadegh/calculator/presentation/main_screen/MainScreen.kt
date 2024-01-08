package com.sadegh.calculator.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sadegh.calculator.presentation.main_screen.components.Buttons
import com.sadegh.calculator.presentation.main_screen.components.ResultBox
import com.sadegh.calculator.presentation.main_screen.util.buttons

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen() {

    val viewModel = viewModel<MainScreenViewModel>()

    val input by viewModel.formattedInput.collectAsState()
    val inputTextColor by viewModel.inputTextColor.collectAsState()
    val inputFontSize by viewModel.inputFontSize.collectAsState()

    val result by viewModel.formattedResult.collectAsState()
    val resultTextColor by viewModel.resultTextColor.collectAsState()
    val resultFontSize by viewModel.resultFontSize.collectAsState()

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
            topText = input,
            topTextFontSize = inputFontSize!!,
            topTextColor = inputTextColor,
            bottomText = result,
            bottomTextFontSize = resultFontSize,
            bottomTextColor = resultTextColor
        )

        val buttons = buttons

        val startIndex by viewModel.startIndex.collectAsState()

        Buttons(
            modifier = Modifier.fillMaxSize(),
            buttons = buttons,
            startIndex = startIndex,
            onEvent = viewModel::onEvent
        )
    }
}







