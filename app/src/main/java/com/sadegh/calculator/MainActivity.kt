package com.sadegh.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadegh.calculator.homeScreen.HomeScreen
import com.sadegh.calculator.ui.theme.CalculatorTheme

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
