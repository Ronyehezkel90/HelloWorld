package com.ronyehezkel.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

//class Person(private val myname:String, private val myAge:Int ){
//    val name:String=myname
//    val age:Int = myAge
//}

class MainActivity : AppCompatActivity() {
    lateinit var person30: Person
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButton3ClickListener()
    }

    fun getName(): String {
        var name: String = "Yakov"
        name = "Ron"
        return name
    }

    private fun setButton3ClickListener() {

        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            val imageView = findViewById<ImageView>(R.id.image_view)
            imageView.setImageResource(R.drawable.banana)
//            createList()
            createRecyclerView()
        }
    }

    private fun giveMeTheName(personList: MutableList<Person>): String {
        for (person: Person in personList) {
            when (person.age) {
                28 -> println("young")
                26 -> println("very young")
                else -> {
                    person30 = person
                    println("old")
                }
            }
        }
        return person30.name
    }

    private fun getPersonList(): MutableList<Person> {
        val personList = mutableListOf<Person>()

        personList.add(Person("Daniella", 30))
        personList.add(Person("Boaz", 26))
        personList.add(Person("Shoham", 28))
        personList.add(Person("Ron"))
        personList.add(Person("Naor", 43))
        personList.add(Person("Mia", 29))
        personList.add(Person("Oriel", 23))
        personList.add(Person("Daniella", 50))


        return personList
    }

    fun onButtonClick(view: View) {
        val textView: TextView = findViewById<TextView>(R.id.hello_text)
        val personList = getPersonList()
        textView.text = giveMeTheName(personList)
    }

    private fun createList() {
        val listView = findViewById<ListView>(R.id.list_view)
        val personList = getPersonList()
        val myAdapter = ArrayAdapter(this, R.layout.item_layout, personList)
        listView.adapter = myAdapter
    }

    private fun createRecyclerView() {
        val listView = findViewById<RecyclerView>(R.id.recycler_view)
        val personList = getPersonList()
        val adapter = MyAdapter(personList)
        listView.adapter = adapter
    }

}