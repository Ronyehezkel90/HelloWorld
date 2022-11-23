package com.ronyehezkel.helloworld.ui.comp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronyehezkel.helloworld.ui.comp.ui.theme.HelloWorldTheme

class CompRegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Registration()
        }
    }
}

@Composable
fun Title(name: String) {
    Text(text = name, color = MaterialTheme.colorScheme.primary)
}

@Composable
fun getButtonText(){
    Text(text = "Button", color = Color.White )
}

@Composable
fun Registration() {
    HelloWorldTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
//                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    Row(modifier = Modifier.padding(end = 10.dp)) {
                        Title("!")
                    }
                    Row() {
                        Title("SignIn Screen")
                    }
                }

                Row() {
                    Button(onClick = { }, content = {getButtonText()})
                }
                Row() {
                    Button(onClick = { }) {

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "lightMode")
@Preview(showBackground = true, name = "darkMode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    Registration()
}