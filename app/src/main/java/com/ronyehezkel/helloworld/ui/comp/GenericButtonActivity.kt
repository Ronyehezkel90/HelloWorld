package com.ronyehezkel.helloworld.ui.comp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData

abstract class GenericButtonActivity : ComponentActivity() {
    //    val myLiveData = MutableLiveData<Boolean>()
    val myState: MutableState<Boolean> = mutableStateOf(false)

    abstract fun buttonClickFunction()

    fun onComplete() {
        myState.value = false
    }

//    abstract fun onComplete()

    fun onButtonClick() {
        myState.value = true
        buttonClickFunction()
    }


}