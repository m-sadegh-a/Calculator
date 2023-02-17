package com.sadegh.calculator.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Buttons(
    modifier: Modifier,
    screen: Screen,
    buttons: Array<Array<Button>>,
    updateScreen: (Screen) -> Unit

) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(7.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        for (row in buttons.indices) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                for (button in buttons[row]) {

                    TextButton(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(button.weight)
                            .padding(vertical = 5.dp, horizontal = 7.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(button.color),
                        onClick = {

                            screen.onClickButtonChange(button.symbolAsString)
                            updateScreen(
                                Screen(
                                    result = screen.result,
                                    input = screen.input
                                )
                            )

                        },

                        ) {

                        if (button.symbolAsIconId != null) {
                            Icon(
                                painter = painterResource(id = button.symbolAsIconId),
                                contentDescription = null,
                                tint = button.contentColor,
                                modifier = Modifier.size(button.iconSize)
                            )
                        } else {
                            Text(
                                text = button.symbolAsString,
                                fontSize = 30.sp,
                                color = button.contentColor,
                            )
                        }
                    }
                }
            }
        }
    }
}







