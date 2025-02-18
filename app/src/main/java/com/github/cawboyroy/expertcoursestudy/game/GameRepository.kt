package com.github.cawboyroy.expertcoursestudy.game

import com.github.cawboyroy.expertcoursestudy.stats.StatsCache

interface GameRepository {

    fun shuffledWord(): String
    fun isCorrect(text: String): Boolean
    fun next()
    fun skip()
    fun saveUserInput(value: String)
    fun userInput(): String
    fun isLastWord(): Boolean

    class Base(
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

        override fun shuffledWord(): String = shuffledList[index.read()]

        override fun isCorrect(text: String): Boolean {
            val isCorrect = originalList[index.read()].equals(text, ignoreCase = true)
            if (isCorrect)
                statsCache.incrementCorrects()
            else statsCache.incrementFails()
        return isCorrect}

        override fun next() {
            val value = index.read()
            index.save(value + 1)
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
            return source
            //todo shuffle letters
        }
    }

    class Reverse : ShuffleStrategy {
        override fun shuffle(source: String): String {
            return source.reversed()
        }
    }
}


