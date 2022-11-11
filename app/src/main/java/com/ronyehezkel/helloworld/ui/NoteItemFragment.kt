package com.ronyehezkel.helloworld.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ronyehezkel.helloworld.MyApp
import com.ronyehezkel.helloworld.R

class NoteItemFragment : Fragment(R.layout.note_item_fragment) {
    override fun onResume() {
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
        super.onResume()
        val activity = requireActivity()
        val personNameTextView = activity.findViewById<TextView>(R.id.person_title)
        val personAgeTextView = activity.findViewById<TextView>(R.id.person_age)
        val age = requireArguments().getInt("thePersonAge")
        val name = requireArguments().getString("thePersonName")
        personNameTextView.text = name
        personAgeTextView.text = "Age is: $age"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
        super.onPause()
    }

    override fun onStop() {
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
        super.onStop()
    }

    override fun onDestroy() {
        object {}.javaClass.enclosingMethod?.name?.let { MyApp.lcLog(this, it) }
        super.onDestroy()
    }
}