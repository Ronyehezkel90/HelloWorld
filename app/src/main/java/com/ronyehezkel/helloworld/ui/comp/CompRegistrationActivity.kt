package com.ronyehezkel.helloworld.ui.comp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronyehezkel.helloworld.GoogleSignInHelper
import com.ronyehezkel.helloworld.R
import com.ronyehezkel.helloworld.model.Repository
import com.ronyehezkel.helloworld.ui.comp.ui.theme.HelloWorldTheme

class CompRegistrationActivity : GenericButtonActivity() {
    val googleSignInHelper = GoogleSignInHelper(this, { openToDoListActivity() }, { onFailLogin() })

    fun openToDoListActivity() {
        super.onComplete()
        Repository(this).updateFcmToken()
        val intent = Intent(this, CompToDoListActivity::class.java)
        startActivity(intent)
    }

    fun onFailLogin() {
        super.onComplete()
    }

    override fun buttonClickFunction() {
        googleSignInHelper.onGoogleSignInClick()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Registration(this)
        }
    }

    @Composable
    fun Title(name: String) {
        Text(text = name, color = MaterialTheme.colorScheme.primary)
    }

    @Composable
    fun getButtonText(text: String) {
        Text(text = text, color = Color.White)
    }

    fun getDaniela(): Boolean {
        Log.d("test", "Daniela")
        return true
    }

    @Composable
    fun Registration(genericButtonActivity: GenericButtonActivity) {
        var isGoogleAuthScreen = remember { mutableStateOf(true) }

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
                            if (isGoogleAuthScreen.value) {
                                Title("Google Auth Screen")
                            } else {
                                Title("Enter By Email")
                            }
                        }
                    }

                    Row {
                        if (isGoogleAuthScreen.value) {
                            MyGenericButton(
                                "Google Sign In",
                                painterResource(id = R.drawable.google_logo),
                                genericButtonActivity
                            )
                        }

                    }
                    Row() {
                        Button(
                            onClick = {
                                isGoogleAuthScreen.value = !isGoogleAuthScreen.value
                            },
                            content = {
                                if (isGoogleAuthScreen.value) {
                                    getButtonText("Enter by Email")
                                } else {
                                    getButtonText("Enter by Google")
                                }
                            })
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
//    Registration(googleSignInHelper.onGoogleSignInClick())
}