package com.ronyehezkel.helloworld

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

//(((((call as ExecutorCallbackCall).delegate as OkHttpCall).rawCall as RealCall).originalRequest as Request).headers as Headers).namesAndValues
interface NotificationsApiInterface {
    @Headers("Content-Type: application/json", "authorization: key=AAAAk0goG_0:APA91bG1Bpz-rWF7DYn5c_CgWjAQgiD1RKTfC9zBpycSUyboWRlYBi52VOUncDdK0-VoegXq0SMH8LGNF6SZwxj8Sdob-IkcBckifsyLenVzXp5x3T_c7mw0dKXUyrutANgT_cuFPx4I")
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