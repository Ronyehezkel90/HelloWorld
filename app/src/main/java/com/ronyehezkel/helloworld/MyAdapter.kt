package com.ronyehezkel.helloworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val dataList: ArrayList<Note>, val onPersonClick: (Note)-> Unit) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.text_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataList[position].title
        holder.textView.setOnClickListener {
            onPersonClick(dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun heyAdapterPleaseUpdateTheView(notesList:List<Note>){
        dataList.clear()
        dataList.addAll(notesList)
        notifyDataSetChanged()
    }
}