package com.github.cawboyroy.expertcoursestudy.load

interface LoadResult {

    fun isSuccessful(): Boolean

    fun message(): String

    data class Error(private val message: String) : LoadResult {

        override fun isSuccessful() = false

        override fun message() = message
    }

    object Success : LoadResult {

        override fun isSuccessful(): Boolean = true

        override fun message(): String = throw IllegalStateException("when success there is no way for error message")
    }
}