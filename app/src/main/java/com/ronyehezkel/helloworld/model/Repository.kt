package com.ronyehezkel.helloworld.model

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.StorageReference
import com.ronyehezkel.helloworld.FirebaseManager
import com.ronyehezkel.helloworld.NotificationsManager
import com.ronyehezkel.helloworld.RepositoryI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlin.concurrent.thread

class Repository private constructor(applicationContext: Context):RepositoryI {
    //    private val notesDao = AppDatabase.getDatabase(applicationContext).getNotesDao()
    private val toDoListDao = AppDatabase.getDatabase(applicationContext).getToDoListDao()
    private val firebaseManager = FirebaseManager.getInstance(applicationContext)
    private val sp = SpManager.getInstance(applicationContext)

    companion object {
        private lateinit var instance: Repository

        fun getInstance(context: Context): Repository {
            if (!Companion::instance.isInitialized) {
                instance = Repository(context)
            }
            return instance
        }
    }

    override fun addUserToToDoList(toDoList: ToDoList, user: User) {
        toDoList.participants.usersList.add(user)
        firebaseManager.updateToDoList(toDoList).addOnSuccessListener {
            thread(start = true) {
                toDoListDao.updateParticipantsList(toDoList.title, toDoList.participants)
                NotificationsManager.sendMessage(
                    "New list!!!",
                    "Hey youve added to list",
                    user.fcmToken
                )
            }
        }
    }

    override fun addNote(toDoList: ToDoList, note: Note) {
        toDoList.notes.notesList.add(note)
        toDoListDao.updateNotesList(toDoList.title, toDoList.notes)
        firebaseManager.updateToDoList(toDoList)
    }

    fun updateNoteImage(toDoList: ToDoList) {
        toDoListDao.updateNotesList(toDoList.title, toDoList.notes)
    }

    fun getPartOfToDoLists(countLetters: Int) {
//        firebaseManager.getPartOfToDoLists(countLetters).addOnSuccessListener {
        val myUserMail = sp.getMyUser().email
        firebaseManager.getAllToDoLists().addOnSuccessListener { toDoListDoc ->
            print(toDoListDoc.documents)
            for (doc in toDoListDoc.documents) {
                if (doc.data != null) {
                    val toDoList = doc.toObject(ToDoList::class.java)
                    toDoList!!.participants.usersList.forEach {
                        if (it.email == myUserMail) {
                            thread(start = true) {
                                toDoListDao.insertToDoList(toDoList)
                            }
                        }
                    }

                }
            }
        }
    }

    fun updateRemoteLists(toDoLists: List<ToDoList>) {
        val myUserMail = sp.getMyUser().email
        firebaseManager.getAllToDoLists().addOnSuccessListener {
            print(it.documents)
            for (doc in it.documents) {
                if (doc.data != null) {
                    val toDoList = doc.toObject(ToDoList::class.java)
                    if (toDoLists.all { l -> l.title != toDoList!!.title })
                        for (user in toDoList!!.participants.usersList) {
                            if (user.email == myUserMail) {
                                thread(start = true) {
                                    toDoListDao.insertToDoList(toDoList)
                                }
                                break
                            }
                        }
                }
            }
        }.addOnFailureListener {

        }
    }

    fun getLocalToDoLists(): LiveData<List<ToDoList>> {
        return toDoListDao.getAllToDoLists()
    }

    override fun addToDoList(toDoList: ToDoList) {
        firebaseManager.updateToDoList(toDoList)
        return toDoListDao.insertToDoList(toDoList)
    }

    override fun getNotesByToDoList(toDoList: ToDoList): LiveData<NotesList> {
        return toDoListDao.getAllNotes(toDoList.title)
    }

    override fun getToDoListByTitle(toDoListTitle: String): ToDoList {
        return toDoListDao.getToDoListByTitle(toDoListTitle)
    }

    override fun getUsersByToDoList(toDoList: ToDoList): LiveData<Participants> {
        return toDoListDao.getAllUsers(toDoList.title)
    }

    fun addProfileImageForUser(uri: Uri) {
        firebaseManager.uploadUserProfileImage(uri)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    fun getProfilePhotoPath(): StorageReference {
        return firebaseManager.getUserProfileImageReference()
    }

    fun updateFcmToken() {
        val myUser = sp.getMyUser()
        firebaseManager.getFcmToken().addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            myUser.fcmToken = task.result
//            firebaseManager.subscribeToTopic()
            firebaseManager.updateUser(myUser)
        })
    }

    fun loadToDoLists() = flow<FlowEvent> {
        val todoListLists = mutableListOf<ToDoList>()
        emit(FlowEvent(Message.DONT_WORRY))

        firebaseManager.getAllToDoLists().await().documents.onEach { doc->
            doc.toObject(ToDoList::class.java)?.let{
                todoListLists.add(it)
            }
        }
        emit(FlowEvent(Message.SUCCESS, toDoList = todoListLists))
    }.catch {
        emit(FlowEvent(Message.FAIL, it.message))
    }

}