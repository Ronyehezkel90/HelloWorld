package com.ronyehezkel.helloworld.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.setPadding
import androidx.lifecycle.viewModelScope
import com.ronyehezkel.helloworld.*
import com.ronyehezkel.helloworld.model.*
import com.ronyehezkel.helloworld.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesActivity : AppCompatActivity() {

    private val notesViewModel: NotesViewModel by viewModels()

    private var chosenNote: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        val serviceIntent = Intent(this, NotesService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    override fun onStart() {
        super.onStart()
        setButtonClickListener()
        val toDoListTitle = intent.extras?.get("title")
        if (toDoListTitle != null) {
            notesViewModel.viewModelScope.launch {
                val toDoList = notesViewModel.getToDoListByTitle(toDoListTitle as String)
                notesViewModel.setCurrentToDoList(toDoList)
            }

        } else {
            displayTitleAlertDialog()
        }
        createRecyclerView()

        val userName = "Ron"
        to_do_list_title_tv.text = "Hello " + userName
    }

    private fun displayPersonDetailsFragment(note: Note) {
        val noteItemFragment = NoteItemFragment()
        val bundle = bundleOf("thePersonAge" to note.description, "thePersonName" to note.title)
        noteItemFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.notes_fragment_container_view, noteItemFragment)
            .commit()
    }

    private fun createNewNote(): Note {
        val noteTitle = note_title_et.text.toString()
        val noteDesc = note_desc_et.text.toString()
        val note = Note(noteTitle, noteDesc)
        notesViewModel.viewModelScope.launch(Dispatchers.IO) {
            notesViewModel.addNote(note)
        }
        return note
    }

    private fun setButtonClickListener() {
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            createNewNote()
        }
    }

    private fun onNoteTitleClick(): (note: Note) -> Unit = {
        displayPersonDetailsFragment(it)
    }

    val getContentFromGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val context = this
            notesViewModel.viewModelScope.launch(Dispatchers.IO) {
                ImagesManager.onImageResultFromGallery(
                    result,
                    chosenNote!!,
                    context,
                    notesViewModel.toDoListLiveData.value!!
                )
            }
        }

    private fun onNoteImageClick(): (note: Note) -> Unit = { note ->
        chosenNote = note
        displayImagesAlertDialog(note, getContentFromGallery)
    }

    private fun displayImagesAlertDialog(note: Note, getContent: ActivityResultLauncher<Intent>) {
        val context = this
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Choose an image")
        alertDialogBuilder.setMessage("Choose image for ${note.title}")
        alertDialogBuilder.setNeutralButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
        alertDialogBuilder.setPositiveButton("Gallery") { dialogInterface: DialogInterface, i: Int ->
            ImagesManager.getImageFromGallery(note, getContent)
        }
        alertDialogBuilder.setNegativeButton("Network") { dialogInterface: DialogInterface, i: Int ->
            notesViewModel.viewModelScope.launch(Dispatchers.IO) {
                Log.d("Test", "${Thread.currentThread().name}")
                ImagesManager.getImageFromApi(
                    note,
                    context,
                    notesViewModel.toDoListLiveData.value!!
                )
            }
        }
        alertDialogBuilder.show()
    }

    private fun displayTitleAlertDialog() {
        val toDoListEditText = EditText(this)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("ToDoList Title")
        alertDialogBuilder.setMessage("Write here your new ToDoList title")
        alertDialogBuilder.setView(toDoListEditText)
        alertDialogBuilder.setPositiveButton("Save") { dialogInterface: DialogInterface, i: Int ->
            val toDoListTitle = toDoListEditText.text.toString()
            val myUser = SpManager.getInstance(this).getMyUser()
            val toDoList = ToDoList(toDoListTitle, Participants(arrayListOf(myUser)))
            notesViewModel.createToDoList(toDoList)
            notesViewModel.setCurrentToDoList(toDoList)
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
            finish()
        }
        alertDialogBuilder.show()
    }

    private fun createRecyclerView() {
        val adapter = MyAdapter(arrayListOf(), onNoteTitleClick(), onNoteImageClick(), this)
        recycler_view.adapter = adapter
        notesViewModel.toDoListLiveData.observe(this) { toDoList ->
            notesViewModel.getNotesLiveData(toDoList).observe(this) {
                if (it != null) {
                    println(it)
                    adapter.heyAdapterPleaseUpdateTheView(it.notesList)
                }
            }
            notesViewModel.getUserLiveData(toDoList).observe(this) {
                users_layout_id.removeAllViews()
                if (it != null) {
                    for (user in it.usersList) {
                        val textView = TextView(this)
                        textView.text = user.firstName.first().toString() + user.lastName.first().toString()
                        textView.setPadding(20)
                        users_layout_id.addView(textView)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun displayAddUserAlertDialog() {
        val toDoListEditText = EditText(this)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Add participant")
        alertDialogBuilder.setMessage("Write here the user's email")
        alertDialogBuilder.setView(toDoListEditText)
        alertDialogBuilder.setPositiveButton("Add") { dialogInterface: DialogInterface, i: Int ->
            val userEmail = toDoListEditText.text.toString()
                notesViewModel.addUser(this, userEmail)
//
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
        }
        alertDialogBuilder.show()
    }

    fun addUserOnClick(view: View) {
        displayAddUserAlertDialog()
    }

}