package com.ronyehezkel.helloworld.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.ronyehezkel.helloworld.GoogleSignInHelper
import com.ronyehezkel.helloworld.R
import com.ronyehezkel.helloworld.Utils
import com.ronyehezkel.helloworld.model.Repository
import com.ronyehezkel.helloworld.model.SpManager
import com.ronyehezkel.helloworld.model.User

class RegistrationActivity : AppCompatActivity() {
    val googleSignInHelper = GoogleSignInHelper(this, { onSuccessfulLogin() })

    var isLoginFragment = true
    val userName = "a@a.com"
    val password = "1234"

    private fun onSuccessfulLogin() {
        openToDoListActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Test", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registartion)
        displayLoginFragment()
        setTextViewClickListener()
    }

    override fun onStart() {
        Log.d("Test", "onStart")
        super.onStart()
        calculateLastLogin()
    }

    fun openToDoListActivity() {
        Repository.getInstance(this).updateFcmToken()
        val intent = Intent(this, ToDoListActivity::class.java)
        startActivity(intent)
    }

    private fun calculateLastLogin() {
        if (Utils.checkIfOneHourPassed(this)) {
            openToDoListActivity()
        }
    }

    override fun onStop() {
        Log.d("Test", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Test", "onDestroy")
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

    private fun displaySignUpFragment() {
        isLoginFragment = false
        findViewById<TextView>(R.id.login_signup_tv).text = "Already a member? Click here to Login"
        val loginFragment = SignUpFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.registration_fragment_container_view, loginFragment)
            .commit()
    }


    private fun displayLoginFragment() {
        isLoginFragment = true
        findViewById<TextView>(R.id.login_signup_tv).text = "Not a member yet? Click here to SignUp"
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.registration_fragment_container_view, loginFragment)
            .commit()
    }

    fun onStartClick(view: View) {
        if (isUserLegit()) {
//            goInApp()
        } else {
            Toast.makeText(this, "Halo Halo you not legit", Toast.LENGTH_LONG).show()
        }

    }

    fun goInApp(googleSignInAccount: GoogleSignInAccount) {
        val myUser = User(
            googleSignInAccount.email!!,
            googleSignInAccount.givenName!!,
            googleSignInAccount.familyName!!,
            ""
        )
        SpManager.getInstance(this).setMyUser(myUser)
        SpManager.getInstance(this).setLastLogin()
        openToDoListActivity()
    }

    private fun isUserLegit(): Boolean {
//        return findViewById<EditText>(R.id.email_login_et).text.toString() == userName &&
//                findViewById<EditText>(R.id.password_login_et).text.toString() == password
        return true
    }
}