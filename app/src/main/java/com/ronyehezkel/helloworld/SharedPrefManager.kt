package com.ronyehezkel.helloworld

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ronyehezkel.helloworld.model.User

class SharedPrefManager private constructor(context: Context) {
    val sharePref = context.getSharedPreferences(R.string.app_name.toString(), AppCompatActivity.MODE_PRIVATE)

//    fun setUser(email: String, firstName:String, lastName:String) {
//        sharePref.edit().putString("USER_EMAIL", email).apply()
//        sharePref.edit().putString("USER_FIRST_NAME", firstName).apply()
//        sharePref.edit().putString("USER_LAST_NAME", lastName).apply()
//    }
//
//    fun getUser(): String {
//        return sharePref.getString("USER_EMAIL", "")!!
//    }

    companion object{
        lateinit var myUser:User

        private lateinit var instance: SharedPrefManager

        fun getInstance(context: Context): SharedPrefManager {
            if(!Companion::instance.isInitialized){
                instance = SharedPrefManager(context)
            }
            return instance
        }
    }

}