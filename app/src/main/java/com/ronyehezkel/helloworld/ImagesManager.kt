package com.ronyehezkel.helloworld

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ronyehezkel.helloworld.model.ApiResponseHitsList
import com.ronyehezkel.helloworld.model.IMAGE_TYPE
import com.ronyehezkel.helloworld.model.Note
import com.ronyehezkel.helloworld.model.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

object ImagesManager {

    fun getImageFromGallery(note: Note, getContent: ActivityResultLauncher<Intent>) {
        Log.d("Test", "Click on ${note.title}")
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    fun onImageResultFromGallery(
        result: ActivityResult,
        chosenNote: Note,
        context: Context
    ) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                addImageToNote(chosenNote, uri.toString(), IMAGE_TYPE.URI, context)
            }
        }
    }

    private fun addImageToNote(
        note: Note,
        imagePath: String,
        imageType: IMAGE_TYPE,
        context: Context
    ) {
        Repository.getInstance(context).updateNoteImage(note, imagePath, imageType)
    }

    fun getImageFromApi(note: Note, context: Context) {
        val retrofit = ApiInterface.create()
        retrofit.getImages(note.title).enqueue(object : Callback<ApiResponseHitsList> {
            override fun onResponse(
                call: Call<ApiResponseHitsList>,
                response: Response<ApiResponseHitsList>
            ) {
                val apiResponse = response.body()
                val apiImage = apiResponse!!.imagesList[3]
                GlobalScope.launch {
                    addImageToNote(note, apiImage.imageUrl, IMAGE_TYPE.URL, context)
                }
            }

            override fun onFailure(call: Call<ApiResponseHitsList>, t: Throwable) {
                Log.e("Wrong api response", t.message.toString())
            }
        })
    }
}