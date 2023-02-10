package com.sadegh.calculator.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Buttons(
    modifier: Modifier,
    buttonsText: Array<Array<String>>,
    input: MutableList<String>,
    updateInput: (MutableList<String>) -> Unit,
    result: String,
    updateResult: (String) -> Unit,

    ) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(7.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        for (row in buttonsText.indices) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                for (buttonText in buttonsText[row]) {

                    val buttonWeight = if (buttonText == "AC" || buttonText == "0") 2f else 1f

                    val buttonColor = getButtonColor(
                        buttonsText = buttonsText,
                        buttonText = buttonText,
                        buttonTextRow = row
                    )


                    TextButton(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(buttonWeight)
                            .padding(vertical = 5.dp, horizontal = 7.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(buttonColor),
                        onClick = {

                            onClickChange(input, buttonText, updateInput, result, updateResult)

                        },

                        ) {

                        Text(
                            text = buttonText,
                            fontSize = 30.sp,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}

fun getButtonColor(
    buttonsText: Array<Array<String>>,
    buttonText: String,
    buttonTextRow: Int
) = when (buttonText) {

    "AC", "DEL" -> Color.Gray

    buttonsText[buttonTextRow].last() -> Color(0xFFFF8C01)

    else -> Color.DarkGray

}






