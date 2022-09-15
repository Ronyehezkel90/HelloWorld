package com.ronyehezkel.helloworld

import android.widget.TextView
import androidx.fragment.app.Fragment

class NoteItemFragment : Fragment(R.layout.note_item_fragment) {
    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        val personNameTextView = activity.findViewById<TextView>(R.id.person_title)
        val personAgeTextView = activity.findViewById<TextView>(R.id.person_age)
        val age = requireArguments().getInt("thePersonAge")
        val name = requireArguments().getString("thePersonName")
        personNameTextView.text = name
        personAgeTextView.text = "Age is: $age"
    }
}