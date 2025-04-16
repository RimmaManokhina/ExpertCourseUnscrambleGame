package com.github.cawboyroy.expertcoursestudy.load.data.cloud

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordsService {

    //https://ao0ixd.buildship.run/api?words=10
    @GET("api")
    fun words(
        @Query("words") wordsCount: Int = 10
    ) : Call<WordsResponseCloud>

}