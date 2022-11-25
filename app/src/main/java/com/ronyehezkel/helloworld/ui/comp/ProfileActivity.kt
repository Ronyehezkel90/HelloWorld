package com.ronyehezkel.helloworld.ui.comp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ronyehezkel.helloworld.R
import com.ronyehezkel.helloworld.ui.comp.ui.theme.HelloWorldTheme
import kotlin.concurrent.thread

class ProfileActivity : GenericButtonActivity() {
    override fun buttonClickFunction() {
        thread(start = true) {
            Thread.sleep(2000)
            super.onComplete()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting(this)
        }
    }
}

@Composable
fun Greeting(genericButtonActivity: GenericButtonActivity) {
    HelloWorldTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Row() {
                MyGenericButton(
                    text = "ProfileButton",
                    icon = painterResource(id = R.drawable.banana),
                    genericButtonActivity = genericButtonActivity
                )
            }

        }
    }}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview2() {
//    HelloWorldTheme {
////        Greeting("Android")
//
//    }
//}