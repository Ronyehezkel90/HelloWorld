package com.ronyehezkel.helloworld

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("?key=29781026-97f646c17609962d293ecf88f&image_type=photo&q=brazil")
    fun getImages(): Call<ApiResponse>

    companion object {
        val BASE_URL = "https://pixabay.com/api/"
        fun create(): ApiInterface {
            val retrofit = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}