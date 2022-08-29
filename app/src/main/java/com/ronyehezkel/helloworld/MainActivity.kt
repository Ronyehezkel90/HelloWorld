package com.ronyehezkel.helloworld

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonClickListener()
    }

    private fun displayPersonDetailsFragment(person: Person) {
        val personFragment = PersonFragment()
        val bundle = bundleOf("thePersonAge" to person.age, "thePersonName" to person.name)
        personFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, personFragment)
            .commit()
    }

    private fun setButtonClickListener() {
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
//            Toast.makeText(this, "Hi Im toast", Toast.LENGTH_LONG).show()
            createRecyclerView()
//            createViewProgrematic()
        }
    }

    private fun createViewProgrematic() {
        val secondTitleTextView = TextView(applicationContext)
        secondTitleTextView.textSize = 30F;
        secondTitleTextView.text = "Hi Im second title";
        val constraintLayout = findViewById<ConstraintLayout>(R.id.screen)
        constraintLayout.addView(secondTitleTextView)
    }

    private fun getPersonList(): MutableList<Person> {
        val personList = mutableListOf<Person>()
        personList.add(Person("Daniella", 30))
        personList.add(Person("Boaz", 26))
        personList.add(Person("Shoham", 28))
        personList.add(Person("Ron", 31))
        personList.add(Person("Naor", 43))
        personList.add(Person("Mia", 29))
        personList.add(Person("Oriel", 23))
        personList.add(Person("Daniella", 50))
        return personList
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val personList = getPersonList()
        val adapter = MyAdapter(personList) {
            displayPersonDetailsFragment(it)
        }
        recyclerView.adapter = adapter
    }

}