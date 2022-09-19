package com.ronyehezkel.helloworld.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    init {
        Log.d("test", "vm start")
    }

    override fun onCleared() {
            Log.d("test", "vm killed")
        super.onCleared()
    }

    var currentEmail = ""
}