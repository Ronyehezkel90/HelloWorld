package com.ronyehezkel.helloworld.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.ronyehezkel.helloworld.R
import com.ronyehezkel.helloworld.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    override fun onStart() {
//        Log.d("Test", "onStartFragment")
        super.onStart()
        email_login_et.setText(registrationViewModel.currentEmail)
        email_login_et.addTextChangedListener {
            registrationViewModel.currentEmail = it.toString()
        }

    }

    override fun onStop() {
//        Log.d("Test", "onStopFragment")
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


}