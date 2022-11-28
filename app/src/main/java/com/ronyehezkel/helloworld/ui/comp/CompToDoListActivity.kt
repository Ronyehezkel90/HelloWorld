package com.ronyehezkel.helloworld.ui.comp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.ronyehezkel.helloworld.model.FlowEvent
import com.ronyehezkel.helloworld.model.Message
import com.ronyehezkel.helloworld.model.Repository
import com.ronyehezkel.helloworld.model.ToDoList
import com.ronyehezkel.helloworld.ui.NotesActivity
import com.ronyehezkel.helloworld.ui.comp.ui.theme.HelloWorldTheme
import com.ronyehezkel.helloworld.viewmodel.ToDoListViewModel
import kotlinx.coroutines.launch

class CompToDoListActivity : ComponentActivity() {
    private val toDoListViewModel: ToDoListViewModel by viewModels()
    private lateinit var repository: Repository
    val flowEventState = mutableStateOf(FlowEvent(Message.DONT_WORRY))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDoListViewModel.viewModelScope.launch {
            loadToDoLists()
        }
        repository = Repository.getInstance(this)
        setContent {
            ToDoListActivityScreen(
                flowEventState = flowEventState,
                onClickFunc = onItemClick()
            )
        }
    }

    private suspend fun loadToDoLists() {
        toDoListViewModel.loadToDoLists().collect {
            if(!it.toDoList.isNullOrEmpty()){
                toDoListViewModel.updateLocalToDoLists()
            }
            flowEventState.value = it
        }
    }

    private fun onItemClick(): (title: String) -> Unit = {
        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("title", it)
        startActivity(intent)
    }
}

@Composable
fun RowItem(toDoList: ToDoList, onClickFunc: (String) -> Unit) {
    Box(modifier = Modifier
        .padding(10.dp)
        .clickable { onClickFunc(toDoList.title) }) {
        Card {
            Text(text = toDoList.title, modifier = Modifier.padding(10.dp))
            Divider(thickness = 2.dp, color = Color.DarkGray)
            for (participant in toDoList.participants.usersList) {
                Text(
                    text = "${participant.firstName} ${participant.lastName} ",
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun MyList(toDoList: List<ToDoList>, onClickFunc: (String) -> Unit) {
    LazyColumn(
    ) {
        items(items = toDoList) {
            Row {
                RowItem(toDoList = it, onClickFunc)
            }
        }
    }
}

@Composable
fun ToDoListActivityScreen(
    flowEventState: MutableState<FlowEvent>,
    onClickFunc: (String) -> Unit
) {
    val myFlowEventState = remember { flowEventState }
    HelloWorldTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (myFlowEventState.value.message) {
                Message.DONT_WORRY -> CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
                Message.SUCCESS -> MyList(myFlowEventState.value.toDoList!!, onClickFunc)
                Message.FAIL -> Text(text = myFlowEventState.value.error!!, color = Color.Red)
            }
        }
    }

}

//@SuppressLint("UnrememberedMutableState")
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview2() {
//    val toDoListsList =
//        listOf(ToDoList("aaaaaa"), ToDoList("bbbbbb"), ToDoList("cccccc"), ToDoList("dddddd"))
//    val myList = mutableStateOf(toDoListsList)
//    ToDoListActivityScreen(myList)
//}