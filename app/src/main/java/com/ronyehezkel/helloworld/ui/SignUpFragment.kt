package com.ronyehezkel.helloworld.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ronyehezkel.helloworld.R
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        google_sign_in_button.setOnClickListener {
            (requireActivity() as RegistrationActivity).googleSignInHelper.onGoogleSignInClick()
        }
    }

    override fun onStop() {
        Log.d("test", "onStop")
        super.onStop()
    }
}