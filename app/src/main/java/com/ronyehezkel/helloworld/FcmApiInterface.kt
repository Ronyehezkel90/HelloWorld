package com.ronyehezkel.helloworld

import com.ronyehezkel.helloworld.model.ApiResponseHitsList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface FcmApiInterface {
    @Headers("Authorization: key=<server_key>", "Content-Type: application/json")
    @POST("/fcm/send")
    fun sendDataNotificationMessage(@Body body:HashMap<String, Any>): Call<Any>

    companion object {
        val BASE_URL = "https://fcm.googleapis.com/"
        fun create(): FcmApiInterface {
            val retrofit = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(FcmApiInterface::class.java)
        }
    }
}