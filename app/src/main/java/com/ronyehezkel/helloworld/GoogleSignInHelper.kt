package com.ronyehezkel.helloworld

import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ronyehezkel.helloworld.model.SpManager
import com.ronyehezkel.helloworld.model.User

class GoogleSignInHelper(val context: ComponentActivity, val onSuccessfulLogin: () -> Unit,val onFailLogin: () -> Unit ) {
    val firebaseAuth = FirebaseAuth.getInstance()

    private val googleGetContent: ActivityResultLauncher<Intent> =
        context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { content ->
            onGoogleIntentResult(content)
        }

    private fun setSignedInUser(googleSignInAccount: GoogleSignInAccount) {
        val myUser = User(
            googleSignInAccount.email!!,
            googleSignInAccount.givenName!!,
            googleSignInAccount.familyName!!,
            ""
        )
        SpManager.getInstance(context).setMyUser(myUser)
        SpManager.getInstance(context).setLastLogin()
        onSuccessfulLogin()
    }

    fun onGoogleSignInClick() {
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .build()
        val googleIntent =
            GoogleSignIn.getClient(context, googleSignInOptions).signInIntent
        googleGetContent.launch(googleIntent)
    }

    private fun loginOrSignUpToFirebase(googleSignInAccount: GoogleSignInAccount) {
        firebaseAuth.fetchSignInMethodsForEmail(googleSignInAccount.email!!)
            .addOnSuccessListener {
                if (it.signInMethods.isNullOrEmpty()) {
                    registerToNotesAppWithFirebase(googleSignInAccount)
                } else {
                    setSignedInUser(googleSignInAccount)
                }
            }
            .addOnFailureListener { displayToast("Failed on firebaseAuth.fetchSignInMethodsForEmail") }
    }

    private fun registerToNotesAppWithFirebase(googleSignInAccount: GoogleSignInAccount) {
        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnSuccessListener {
                val newUser = User(
                    googleSignInAccount.email!!,
                    googleSignInAccount.givenName!!,
                    googleSignInAccount.familyName!!,
                    ""
                )
                FirebaseManager.getInstance(context).updateUser(newUser)
                setSignedInUser(googleSignInAccount)
            }
            .addOnFailureListener {
                displayToast("Please try again later Exception: ${it.message}")
            }
    }

    private fun onGoogleIntentResult(content: ActivityResult) {
        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(content.data)
        task.addOnSuccessListener {
            loginOrSignUpToFirebase(it)
        }.addOnFailureListener {
            onFailLogin()
//            isProcessingLiveData.value = false
            displayToast("If you can't use GoogleSignIn please sign in the regular way")
        }
    }

    private fun displayToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}