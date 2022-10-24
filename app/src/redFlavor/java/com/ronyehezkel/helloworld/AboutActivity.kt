package com.ronyehezkel.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}

private fun getUpdatedToDoLists(allToDoListsLiveData: LiveData<List<ToDoList>>) {
    firebaseManager.getToDoListPagination().addOnSuccessListener {
        for (d in it.documents) {
            if (d.data != null) {
                val toDoList = d.toObject(ToDoList::class.java)
                if (!allToDoListsLiveData.value!!.contains(toDoList)) {
                    thread(start = true) {
                        toDoListDao.insertToDoList(toDoList!!)
                    }
                }
            }
        }
    }
}

fun getAllToDoLists(): LiveData<List<ToDoList>> {
    val allToDoListsLiveData =  toDoListDao.getAllToDoLists()
    getUpdatedToDoLists(allToDoListsLiveData)
    return allToDoListsLiveData
}