package com.ronyehezkel.helloworld

import android.content.Context
import com.ronyehezkel.helloworld.model.SpManager

object Utils {
    fun checkIfOneHourPassed(context: Context): Boolean {
        val sharedPref = SpManager.getInstance(context)
        val lastLogin = sharedPref.getLastLogin()
        val ONE_HOUR_IN_MILLI_SEC = 3600000
        return lastLogin != -1L && System.currentTimeMillis() - lastLogin < ONE_HOUR_IN_MILLI_SEC
    }
}