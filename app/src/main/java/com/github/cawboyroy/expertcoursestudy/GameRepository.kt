package com.github.cawboyroy.expertcoursestudy

interface GameRepository {

    fun shuffledWord(): String

    fun originalWord(): String

    fun next()

    class Base(
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

        private val shuffledList: List<String> = originalList.map {it.reversed()}

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


