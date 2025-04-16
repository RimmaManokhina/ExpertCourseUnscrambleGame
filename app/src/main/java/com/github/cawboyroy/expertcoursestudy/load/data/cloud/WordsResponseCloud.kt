package com.github.cawboyroy.expertcoursestudy.load.data.cloud

import com.google.gson.annotations.SerializedName

data class WordsResponseCloud (
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val words: List<String>
)
