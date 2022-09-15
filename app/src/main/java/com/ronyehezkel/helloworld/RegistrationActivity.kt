package com.ronyehezkel.helloworld

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar.HOUR

class RegistrationActivity : AppCompatActivity() {
    var isLoginFragment = true
    val userName = "a@a.com"
    val password = "1234"
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registartion)
        displayLoginFragment()
        setTextViewClickListener()
    }

    override fun onStart() {
        super.onStart()
        sharedPreferences = getSharedPreferences(R.string.app_name.toString(), MODE_PRIVATE)
        calculateLastLogin()
    }

    private fun calculateLastLogin() {
        val lastLogin = sharedPreferences.getLong("LAST_LOGIN", -1)
        if (lastLogin != -1L && System.currentTimeMillis() - lastLogin < 3600000) {
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setTextViewClickListener() {
        findViewById<TextView>(R.id.login_signup_tv).setOnClickListener {
            if (isLoginFragment) {
                displaySignUpFragment()
            } else {
                displayLoginFragment()
            }
        }
    }

    fun displaySignUpFragment() {
        isLoginFragment = false
        findViewById<TextView>(R.id.login_signup_tv).text = "Already a member? Click here to Login"
        val loginFragment = SignUpFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.registration_fragment_container_view, loginFragment)
            .commit()
    }


    fun displayLoginFragment() {
        isLoginFragment = true
        findViewById<TextView>(R.id.login_signup_tv).text = "Not a member yet? Click here to SignUp"
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.registration_fragment_container_view, loginFragment)
            .commit()
    }

    fun onStartClick(view: View) {
        if (isUserLegit()) {
            val editor = sharedPreferences.edit()
            editor.putLong("LAST_LOGIN", System.currentTimeMillis()).apply()
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Halo Halo you not legit", Toast.LENGTH_LONG).show()
        }

    }

    private fun isUserLegit(): Boolean {
        return findViewById<EditText>(R.id.email_login_tv).text.toString() == userName &&
                findViewById<EditText>(R.id.password_login_tv).text.toString() == password
    }
}