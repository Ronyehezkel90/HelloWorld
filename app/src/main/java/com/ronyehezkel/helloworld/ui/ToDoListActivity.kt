package com.ronyehezkel.helloworld.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.ronyehezkel.helloworld.R
import com.ronyehezkel.helloworld.ToDoListAdapter
import com.ronyehezkel.helloworld.model.ToDoList
import com.ronyehezkel.helloworld.viewmodel.ToDoListViewModel
import kotlinx.android.synthetic.main.activity_to_do_list.*

class ToDoListActivity : AppCompatActivity() {
    private val toDoListViewModel: ToDoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        toDoListViewModel.getAllToDoListsAsLiveData().observe(this) {
            grid_view.adapter = ToDoListAdapter(this, it, onToDoListClick())
        }
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
}