package com.ronyehezkel.helloworld

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ronyehezkel.helloworld.model.Note
import com.ronyehezkel.helloworld.model.ToDoList
import kotlinx.android.synthetic.main.card_item.view.*

class ToDoListAdapter(
    context: Context,
    toDoListArrayList: List<ToDoList>,
    val onToDoListClick: (toDoList: ToDoList) -> Unit,
) :
    ArrayAdapter<ToDoList>(context, 0, toDoListArrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
        }
        val toDoList: ToDoList? = getItem(position)
        listItemView!!.idTVCourse.text = toDoList?.title
        listItemView.setOnClickListener {
            onToDoListClick(toDoList!!)
        }
        return listItemView
    }
}

