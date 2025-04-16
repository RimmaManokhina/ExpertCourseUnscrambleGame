package com.github.cawboyroy.expertcoursestudy.game.di

import android.content.Context
import com.github.cawboyroy.expertcoursestudy.R
import com.github.cawboyroy.expertcoursestudy.stats.data.StatsCache

class Core(context: Context, val clearViewModel: ClearViewModel) {

    val sharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    val statsCache: StatsCache.All = StatsCache.Base(sharedPreferences)
}