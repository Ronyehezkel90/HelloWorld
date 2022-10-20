package com.ronyehezkel.helloworld

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import com.ronyehezkel.helloworld.model.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ImagesManager {

    fun getImageFromGallery(getContent: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    private fun addImageToUser(context: Context, imagePath: Uri?): UploadTask {
        return FirebaseManager.getInstance(context).uploadUserProfile(imagePath!!)
    }

    fun onProfilePhotoResultFromGallery(
        result: ActivityResult,
        context: Context
    ) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                addImageToUser(context, uri).addOnSuccessListener {
                    FirebaseManager.getInstance(context)
                        .getUserProfileRef().downloadUrl.addOnSuccessListener {
                            val spManager = SpManager.getInstance(context)
                            val myUser = spManager.getMyUser()
                            myUser.imagePath = it.path
                            spManager.setMyUser(myUser)
                            FirebaseManager.getInstance(context).updateUser(myUser)
                        }
                }
            }
        }
    }

    fun onImageResultFromGallery(
        result: ActivityResult,
        chosenNote: Note,
        context: Context,
        toDoList: ToDoList
    ) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                addImageToNote(chosenNote, uri.toString(), IMAGE_TYPE.URI, context, toDoList)
            }
        }
    }

    private fun addImageToNote(
        note: Note,
        imagePath: String,
        imageType: IMAGE_TYPE,
        context: Context,
        toDoList: ToDoList
    ) {
        toDoList.notes.notesList.forEach {
            if (it.title == note.title) {
                it.imagePath = imagePath
                it.imageType = imageType
            }
        }
        Repository.getInstance(context).updateNoteImage(toDoList)
    }

    fun getImageFromApi(note: Note, context: Context, toDoList: ToDoList) {
        val retrofit = ApiInterface.create()
        retrofit.getImages(note.title).enqueue(object : Callback<ApiResponseHitsList> {
            override fun onResponse(
                call: Call<ApiResponseHitsList>,
                response: Response<ApiResponseHitsList>
            ) {
                val apiResponse = response.body()
                val apiImage = apiResponse!!.imagesList[3]
                GlobalScope.launch {
                    addImageToNote(note, apiImage.imageUrl, IMAGE_TYPE.URL, context, toDoList)
                }
            }

            override fun onFailure(call: Call<ApiResponseHitsList>, t: Throwable) {
                Log.e("Wrong api response", t.message.toString())
            }
        })
    }

    fun getProfilePhoto(context: Context): Task<ByteArray> {
        return FirebaseManager.getInstance(context).getUserProfile()
    }
}