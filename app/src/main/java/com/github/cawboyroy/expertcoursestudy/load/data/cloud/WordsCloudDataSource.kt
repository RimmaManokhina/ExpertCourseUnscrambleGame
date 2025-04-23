package com.github.cawboyroy.expertcoursestudy.load.data.cloud

interface WordsCloudDataSource {

    suspend fun words() : List<String>

    class Base(
        private val service: Int,
        create: WordsService,
    ) : WordsCloudDataSource {

        override suspend fun words(): List<String> {
                val data = service.words().execute()
                return data.body()!!.words
        }
    }
}