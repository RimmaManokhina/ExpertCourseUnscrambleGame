package com.github.cawboyroy.expertcoursestudy.core

import android.content.Context
import androidx.room.Room
import com.github.cawboyroy.expertcoursestudy.R
import com.github.cawboyroy.expertcoursestudy.game.data.IntCache
import com.github.cawboyroy.expertcoursestudy.game.di.ClearViewModel
import com.github.cawboyroy.expertcoursestudy.load.RunAsync
import com.github.cawboyroy.expertcoursestudy.load.data.cache.WordsDao
import com.github.cawboyroy.expertcoursestudy.load.data.cache.WordsDatabase
import com.github.cawboyroy.expertcoursestudy.stats.data.StatsCache

class Core(val context: Context, val clearViewModel: ClearViewModel) {

    val runUiTests = false

    val runAsync: RunAsync = RunAsync.Base()

    val sharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)!!

    val statsCache: StatsCache.All = StatsCache.Base(sharedPreferences)

    val indexCache = IntCache.Base(sharedPreferences, "indexKey", Int.MIN_VALUE)

    val wordsSize = if (runUiTests) 2 else 10

    val database by lazy {
        Room.databaseBuilder<WordsDatabase>(
            context,
            WordsDatabase::class.java,
            context.getString(R.string.app_name)
        )
            .build()
    }

    fun dao(): WordsDao = database.dao()
}