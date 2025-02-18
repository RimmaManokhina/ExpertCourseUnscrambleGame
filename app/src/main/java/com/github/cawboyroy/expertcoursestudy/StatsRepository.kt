package com.github.cawboyroy.expertcoursestudy

interface StatsRepository {

    fun stats(): Triple<Int, Int, Int>

    fun clear()
}
