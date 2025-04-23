package com.github.cawboyroy.expertcoursestudy.game.data

import com.github.cawboyroy.expertcoursestudy.game.data.StringCache
import com.github.cawboyroy.expertcoursestudy.stats.data.StatsCache
import com.github.cawboyroy.expertcoursestudy.load.data.cache.WordsCacheDataSource

interface GameRepository {

    suspend fun shuffledWord(): String
    suspend fun isCorrect(text: String): Boolean
    fun next()
    fun skip()
    fun saveUserInput(value: String)
    fun userInput(): String
    fun isLastWord(): Boolean

    class Base(
        private val wordsSize: Int,
        private val cacheDataSource: WordsCacheDataSource.Read,
        private val statsCache: StatsCache.Game,
        private val index: IntCache,
        private val userInput: StringCache,
        private val shuffleStrategy: ShuffleStrategy = ShuffleStrategy.Base(),
    ) : GameRepository {

        override suspend fun shuffledWord(): String {
            val word = cacheDataSource.word(index.read())
            return shuffleStrategy.shuffle(word)
        }

        override suspend fun isCorrect(text: String): Boolean {
            val isCorrect = cacheDataSource.word(index.read()).equals(text, ignoreCase = true)
            if (isCorrect)
                statsCache.incrementCorrects()
            else
                statsCache.incrementFails()
            if (isLastWord())
                index.save(Int.MIN_VALUE)
            return isCorrect
        }

        override fun next() {
            index.save(index.read() + 1)
            userInput.save("")
        }

        override fun skip() {
            statsCache.incrementSkips()
            next()
            if (isLastWord())
                index.save(Int.MIN_VALUE)
        }

        override fun saveUserInput(value: String) {
            userInput.save(value)
        }

        override fun userInput(): String {
            return userInput.read()
        }

        override fun isLastWord(): Boolean {
            val read = index.read()
            return read == wordsSize || read < 0
        }
    }

    class Fake(
        private val statsCache: StatsCache.Game,
        private val index: IntCache,
        private val userInput: StringCache,
        private val shuffleStrategy: ShuffleStrategy = ShuffleStrategy.Base(),
        private val originalList: List<String> = listOf(
            "facts",
            "never",
            "entertain",
            "alligator",
            "left"
        )
    ) : GameRepository {

        private val shuffledList = originalList.map { shuffleStrategy.shuffle(it) }

        override suspend fun shuffledWord(): String = shuffledList[index.read()]

        override suspend fun isCorrect(text: String): Boolean {
            val isCorrect = originalList[index.read()].equals(text, ignoreCase = true)
            if (isCorrect)
                statsCache.incrementCorrects()
            else
                statsCache.incrementFails()
            return isCorrect
        }


        override fun next() {
            index.save(index.read() + 1)
            userInput.save("")
        }

        override fun skip() {
            statsCache.incrementSkips()
            next()
        }

        override fun saveUserInput(value: String) {
            userInput.save(value)
        }

        override fun userInput(): String {
            return userInput.read()
        }

        override fun isLastWord(): Boolean {
            val lastWord = index.read() == originalList.size
            if (lastWord)
                index.save(0)
            return lastWord
        }
    }
}

interface ShuffleStrategy {

    fun shuffle(source: String): String

    class Base : ShuffleStrategy {

        override fun shuffle(source: String): String {
            val list = mutableListOf<Char>()
            source.forEach {
                list.add(it)
            }
            val shuffled = list.shuffled()
            val stringBuilder = StringBuilder()
            shuffled.forEach {
                stringBuilder.append(it)
            }
            return stringBuilder.toString()
        }
    }

    class Reverse : ShuffleStrategy {

        override fun shuffle(source: String): String {
            return source.reversed()
        }
    }
}