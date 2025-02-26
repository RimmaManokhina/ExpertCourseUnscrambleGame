package com.github.cawboyroy.expertcoursestudy.game

import android.content.SharedPreferences

interface StringCache {

    fun read(): String
    fun save(value: String)

    class Base(
        private val sharedPreferences: SharedPreferences,
        private val key: String,
        private val default: String = ""
    ) : StringCache {
        override fun read(): String {
            return sharedPreferences.getString(key, default) ?: default
        }

        override fun save(value: String) {
            sharedPreferences.edit().putString(key, value). apply()
        }

    }
}