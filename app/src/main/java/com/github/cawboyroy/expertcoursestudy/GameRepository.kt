package com.github.cawboyroy.expertcoursestudy

interface GameRepository {

    fun shuffledWord(): String

    fun originalWord(): String

    fun next()

    class Base(
        private val shuffleStrategy: ShuffleStrategy = ShuffleStrategy.Base(),

        private val originalList: List<String> = listOf(
            "facts",
            "never",
            "entertain",
            "alligator",
            "left",
            "handle",
            "panda",
            "effort",
            "January",
            "extra",
            "camera",
            "plant",
            "every",
            "exit",
            "spelling",
            "hello",
            "clever"
        )
    ) : GameRepository {

        private val shuffledList = originalList.map { shuffleStrategy.shuffle(it) }

        private var index = 0

        override fun shuffledWord(): String = shuffledList[index]

        override fun originalWord(): String = originalList[index]

        override fun next() {
            if (index + 1 == originalList.size)
                index = 0
            else
                index++
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


