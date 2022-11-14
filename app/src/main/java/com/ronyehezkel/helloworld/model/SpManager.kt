package com.ronyehezkel.helloworld.model

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ronyehezkel.helloworld.R

class SpManager private constructor(context: Context) {

    val sharedPrefs = context.getSharedPreferences(R.string.app_name.toString(), AppCompatActivity.MODE_PRIVATE)

    companion object {
        private lateinit var instance: SpManager

        fun getInstance(context: Context): SpManager {
            if (!Companion::instance.isInitialized) {
                instance = SpManager(context)
            }
            return instance
        }
    }

    fun setMyUser(user: User) {
        sharedPrefs.edit()
            .putString(firstNameKey, user.firstName)
            .putString(lastNameKey, user.lastName)
            .putString(emailKey, user.email)
            .apply()
    }

    fun getMyUser(): User {
        val firstName = sharedPrefs.getString(firstNameKey, "")!!
        val lastName = sharedPrefs.getString(lastNameKey, "")!!
        val email = sharedPrefs.getString(emailKey, "")!!
        val fcmToken = sharedPrefs.getString(fcmTokenKey, "")!!
        return User(email, firstName, lastName, fcmToken)
    }

    fun setLastLogin() {
        sharedPrefs.edit().putLong(lastLoginKey, System.currentTimeMillis()).apply()
    }

    fun getLastLogin(): Long {
        return sharedPrefs.getLong(lastLoginKey, -1)
    }

    private val firstNameKey = "FIRST_NAME"
    private val lastNameKey = "LAST_NAME"
    private val emailKey = "EMAIL"
    private val lastLoginKey = "LAST_LOGIN"
    private val fcmTokenKey = "FCM_TOKEN"

}