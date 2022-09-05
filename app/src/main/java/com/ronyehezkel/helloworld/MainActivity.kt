package com.ronyehezkel.helloworld

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        setButtonClickListener()
        repository = Repository(application)
        createRecyclerView()
    }

    private fun displayPersonDetailsFragment(note: Note) {
        val personFragment = PersonFragment()
        val bundle = bundleOf("thePersonAge" to note.description, "thePersonName" to note.title)
        personFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, personFragment)
            .commit()
    }

    private fun setButtonClickListener() {
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val noteTitle = findViewById<EditText>(R.id.note_title_et).text.toString()
            val noteDesc = findViewById<EditText>(R.id.note_desc_et).text.toString()
            val note = Note(noteTitle, noteDesc)
            thread(start = true) {
                repository.addNote(note)
            }
        }
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = MyAdapter(arrayListOf()) {
            displayPersonDetailsFragment(it)
        }
        recyclerView.adapter = adapter
        val notesListLiveData = repository.getAllNotesAsLiveData()
        notesListLiveData.observe(this) { notesList ->
            adapter.heyAdapterPleaseUpdateTheView(notesList)
        }

    }

}