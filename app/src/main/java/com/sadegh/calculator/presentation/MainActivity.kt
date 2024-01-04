package com.sadegh.calculator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadegh.calculator.presentation.main_screen.HomeScreen
import com.sadegh.calculator.ui.theme.CalculatorTheme
import kotlinx.coroutines.flow.flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            CalculatorTheme(true) {
                HomeScreen()

            }
        }
    }
}
