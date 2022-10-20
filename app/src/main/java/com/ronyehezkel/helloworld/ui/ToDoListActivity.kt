package com.ronyehezkel.helloworld.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.ronyehezkel.helloworld.AboutActivity
import com.ronyehezkel.helloworld.ImagesManager
import com.ronyehezkel.helloworld.R
import com.ronyehezkel.helloworld.ToDoListAdapter
import com.ronyehezkel.helloworld.model.SpManager
import com.ronyehezkel.helloworld.model.ToDoList
import com.ronyehezkel.helloworld.viewmodel.ToDoListViewModel
import kotlinx.android.synthetic.main.activity_to_do_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ToDoListActivity : AppCompatActivity() {
    private val toDoListViewModel: ToDoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        toDoListViewModel.getAllToDoListsAsLiveData().observe(this) {
            grid_view.adapter = ToDoListAdapter(this, it, onToDoListClick())
        }
        loadProfilePhotoByRef()
    }

    private fun onToDoListClick(): (toDoList: ToDoList) -> Unit = {
        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("title", it.title)
        startActivity(intent)
    }

    fun onAddToDoListClick(view: View) {
        val intent = Intent(this, NotesActivity::class.java)
        startActivity(intent)
    }

    val getContentFromGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val context = this
            toDoListViewModel.viewModelScope.launch(Dispatchers.IO) {
                ImagesManager.onProfilePhotoResultFromGallery(result, context)
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun onProfileButtonClick(view: View) {
        ImagesManager.getImageFromGallery(getContentFromGallery)
    }

    private fun loadProfilePhotoByRef() {
        ImagesManager.getProfilePhoto(this).addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            profile_photo_button.setImageBitmap(
                Bitmap.createScaledBitmap(
                    bmp,
                    profile_photo_button.width,
                    profile_photo_button.height,
                    false
                )
            )
        }.addOnFailureListener {
            print(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                val i = Intent(this, AboutActivity::class.java)
                this.startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}