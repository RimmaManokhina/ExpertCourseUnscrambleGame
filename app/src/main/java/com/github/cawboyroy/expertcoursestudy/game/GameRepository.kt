package com.github.cawboyroy.expertcoursestudy.game

interface GameRepository {

    fun shuffledWord(): String
    fun originalWord(): String
    fun next()
    fun saveUserInput(value: String)
    fun userInput(): String

    class Base(
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

        override fun originalWord(): String = originalList[index.read()]

        override fun next() {
            val value = index.read()

            if (value + 1 == originalList.size)
                index.save(0)
            else
                index.save(value + 1)
            userInput.save("")
        }

        override fun saveUserInput(value: String) {
            userInput.save(value)
        }

        override fun userInput(): String {
            return userInput.read()
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


