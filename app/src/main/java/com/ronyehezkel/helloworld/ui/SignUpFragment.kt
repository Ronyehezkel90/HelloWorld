package com.ronyehezkel.helloworld.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ronyehezkel.helloworld.Firestore
import com.ronyehezkel.helloworld.R
import com.ronyehezkel.helloworld.model.User
import com.ronyehezkel.helloworld.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    private val registrationViewModel: RegistrationViewModel by activityViewModels()
    private lateinit var googleGetContent: ActivityResultLauncher<Intent>
    val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        googleGetContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { content ->
                onGoogleIntentResult(content)
            }

        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onStart() {
        Log.d("test", "onStart")
        super.onStart()

        setOnGoogleSignInClickListener()
    }

    private fun setOnGoogleSignInClickListener() {
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()
        val googleIntent =
            GoogleSignIn.getClient(requireActivity(), googleSignInOptions).signInIntent
        google_sign_in_button.setOnClickListener {

            googleGetContent.launch(googleIntent)
        }
    }

    private fun onGoogleIntentResult(content: ActivityResult) {
        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(content.data)
        task
            .addOnSuccessListener {
                loginOrSignUpToFirebase(it)
            }
            .addOnFailureListener {
                displayToast("If you can't use GoogleSignIn please sign in the regular way")
            }
    }

    private fun loginOrSignUpToFirebase(googleSignInAccount: GoogleSignInAccount) {
        firebaseAuth.fetchSignInMethodsForEmail(googleSignInAccount.email!!)
            .addOnSuccessListener {
                if (it.signInMethods.isNullOrEmpty()) {
                    registerToNotesAppWithFirebase(googleSignInAccount)
                } else {
                    getIntoApp(googleSignInAccount)
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
                    googleSignInAccount.familyName!!
                )
                Firestore.getInstance(requireContext()).addUser(newUser)
                getIntoApp(googleSignInAccount)
            }
            .addOnFailureListener {
                displayToast("Please try again later Exception: ${it.message}")
            }
    }


    private fun getIntoApp(googleSignInAccount: GoogleSignInAccount) {
        (requireActivity() as RegistrationActivity).goInApp(googleSignInAccount)
    }

    fun displayToast(text: String) {
        Toast.makeText(requireActivity(), text, Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        Log.d("test", "onStop")
        super.onStop()
    }
}