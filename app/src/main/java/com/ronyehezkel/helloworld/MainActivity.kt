package com.ronyehezkel.helloworld

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var repository: Repository
    private var chosenNote: Note? = null
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

    private fun onNoteTitleClick(): (note: Note) -> Unit = {
        displayPersonDetailsFragment(it)
    }

    val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("Test", "got content: $result")
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    addImageToNote(uri.toString(), IMAGE_TYPE.URI)
                }
            }
        }

    private fun addImageToNote(imagePath: String, imageType: IMAGE_TYPE) {
        thread(start = true) {
            repository.updateNoteImage(chosenNote!!, imagePath, imageType)
        }
    }

    private fun getImageFromGallery(note: Note) {
        Log.d("Test", "Click on ${note.title}")
        chosenNote = note
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    private fun onNoteImageClick(): (note: Note) -> Unit = { note ->
//        getImageFromGallery(note)
        getImageFromApi(note)
    }

    private fun getImageFromApi(note:Note) {
        chosenNote = note
        val retrofit = ApiInterface.create()
        retrofit.getImages().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val apiResponse = response.body()
                val apiImage = apiResponse!!.imagesList[3]
                addImageToNote(apiImage.imageUrl, IMAGE_TYPE.URL)
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Wrong api response", t.message.toString())
            }
        })
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = MyAdapter(arrayListOf(), onNoteTitleClick(), onNoteImageClick(), this)
        recyclerView.adapter = adapter
        val notesListLiveData = repository.getAllNotesAsLiveData()
        notesListLiveData.observe(this) { notesList ->
            adapter.heyAdapterPleaseUpdateTheView(notesList)
        }
    }

}