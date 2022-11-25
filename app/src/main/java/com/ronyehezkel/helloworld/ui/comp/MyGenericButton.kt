package com.ronyehezkel.helloworld.ui.comp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGenericButton(
    text: String,
    icon: Painter,
    genericButtonActivity: GenericButtonActivity
) {
    val isClicked = remember { genericButtonActivity.myState }
    Surface(
        border = BorderStroke(width = 2.dp, color = Color.DarkGray),
        enabled = !isClicked.value,
        onClick = {
            isClicked.value = true
            genericButtonActivity.onButtonClick()
        }
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isClicked.value) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Icon(
                    painter = icon,
                    contentDescription = "Icon Image",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
            }
            Text(text = text)
        }
    }

}