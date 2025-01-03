package com.github.cawboyroy.expertcoursestudy

interface GameRepository {

    fun shuffledWord(): String

    fun originalWord(): String

    fun next()
}
