package com.github.cawboyroy.expertcoursestudy.load

interface LoadRepository {

    fun load(resultCallBack: (LoadResult) -> Unit)
}