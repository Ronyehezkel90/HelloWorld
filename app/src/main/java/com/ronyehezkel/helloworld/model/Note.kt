package com.ronyehezkel.helloworld.model

import androidx.room.Entity
import androidx.room.PrimaryKey


enum class IMAGE_TYPE {
    URI, URL
}
@Entity
data class Note(
    val title: String,
    val description: String,
    var imagePath: String? = null,
    var imageType: IMAGE_TYPE? = null,
    var timestamp: Long = System.currentTimeMillis()
)
{
    constructor():this("", "")
    @PrimaryKey(autoGenerate = true)
    var id = 0
}