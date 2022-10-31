package com.ronyehezkel.helloworld.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.ronyehezkel.helloworld.R
import com.ronyehezkel.helloworld.ToDoListAdapter
import com.ronyehezkel.helloworld.model.Repository
import com.ronyehezkel.helloworld.model.ToDoList
import com.ronyehezkel.helloworld.viewmodel.ToDoListViewModel
import kotlinx.android.synthetic.main.activity_to_do_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoListActivity : AppCompatActivity() {
    private val toDoListViewModel: ToDoListViewModel by viewModels()
    private lateinit var repository:Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        toDoListViewModel.getAllToDoListsAsLiveData().observe(this) {
            grid_view.adapter = ToDoListAdapter(this, it, onToDoListClick())
        }
        repository = Repository.getInstance(this)
    }

    override fun onStart() {
        super.onStart()
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

    private fun displayProfileImageIfExist(){
        val profilePhotoPath = repository.getProfilePhotoPath()
        profilePhotoPath.downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(profile_photo_button);
        }
    }

    val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val context = this
            toDoListViewModel.viewModelScope.launch(Dispatchers.IO) {
                if (result.resultCode == RESULT_OK) {
                    val uri = result.data?.data
                    if (uri != null) {
                        context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        repository.addProfileImageForUser(uri)
                    }
                }
            }
        }

    fun onProfileButtonClick(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    fun onDownloadMoreToDoLists(view: View) {
        toDoListViewModel.updateLocalToDoLists()
    }
}