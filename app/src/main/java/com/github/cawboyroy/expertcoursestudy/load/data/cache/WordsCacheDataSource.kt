package com.github.cawboyroy.expertcoursestudy.load.data.cache

interface WordsCacheDataSource {

    interface Save {

        suspend fun save(words: List<String>)
    }

    interface Read {

        suspend fun word(index: Int): String
    }

    interface Mutable : Save, Read

    class Base(
        private val dao: WordsDao
    ) : Mutable {
        override suspend fun save(words: List<String>) {
            dao.save(words.mapIndexed { index, word ->
                WordEntity(index, word)
            })
        }

        override suspend fun word(index: Int): String {
            val word = dao.word(index)
            return word.text
        }
    }
}