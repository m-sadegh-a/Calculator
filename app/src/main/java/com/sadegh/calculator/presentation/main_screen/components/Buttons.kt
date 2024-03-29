package com.sadegh.calculator.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadegh.calculator.presentation.main_screen.util.Button
import com.sadegh.calculator.presentation.main_screen.UserEvent

@Composable
fun Buttons(
    modifier: Modifier,
    startIndex: Int,
    buttons: List<List<Button>>,
    onEvent: (UserEvent) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(7.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        for (row in startIndex until buttons.size) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                for (column in startIndex until buttons[row].size) {

                    val button = buttons[row][column]
                    TextButton(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(vertical = 5.dp, horizontal = 7.dp)
                            .clip(RoundedCornerShape(if (startIndex == 1) 30.dp else 20.dp))
                            .background(button.color),
                        onClick = {

                            button.event?.let(onEvent)

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
                                text = button.symbolAsString!!,
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








