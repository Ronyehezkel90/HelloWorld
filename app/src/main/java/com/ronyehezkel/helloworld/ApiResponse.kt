package com.ronyehezkel.helloworld

import com.google.gson.annotations.SerializedName

data class ApiImage(@SerializedName("webformatURL") val imageUrl:String)

data class ApiResponse(@SerializedName("hits") val imagesList:List<ApiImage>)