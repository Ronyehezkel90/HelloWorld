package com.ronyehezkel.helloworld.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.ronyehezkel.helloworld.R
import com.ronyehezkel.helloworld.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onStart() {
        Log.d("test", "onStart")
        super.onStart()
        email_signup_et.setText(registrationViewModel.currentEmail)
        email_signup_et.addTextChangedListener {
            registrationViewModel.currentEmail = it.toString()
        }
    }

    override fun onStop() {
        Log.d("test", "onStop")
        super.onStop()
    }
}