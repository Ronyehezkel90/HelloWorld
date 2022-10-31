package com.ronyehezkel.helloworld

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

//(((((call as ExecutorCallbackCall).delegate as OkHttpCall).rawCall as RealCall).originalRequest as Request).headers as Headers).namesAndValues
interface NotificationsApiInterface {
    @Headers("Content-Type: application/json", "authorization: key={token_key}")
    @POST("fcm/send")
    fun sendNotification(@Body requestBody: HashMap<String, Any>): Call<Any>

    companion object {
        val BASE_URL = "https://fcm.googleapis.com/"

        fun create(): NotificationsApiInterface {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NotificationsApiInterface::class.java)

        }
    }
}