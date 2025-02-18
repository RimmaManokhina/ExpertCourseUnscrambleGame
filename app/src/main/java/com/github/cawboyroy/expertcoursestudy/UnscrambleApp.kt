package com.github.cawboyroy.expertcoursestudy

import android.app.Application
import android.content.Context
import com.github.cawboyroy.expertcoursestudy.game.GameRepository
import com.github.cawboyroy.expertcoursestudy.game.GameViewModel
import com.github.cawboyroy.expertcoursestudy.game.IntCache
import com.github.cawboyroy.expertcoursestudy.game.ShuffleStrategy
import com.github.cawboyroy.expertcoursestudy.game.StringCache
import com.github.cawboyroy.expertcoursestudy.stats.StatsCache
import com.github.cawboyroy.expertcoursestudy.stats.StatsViewModel

class UnscrambleApp : Application() {

    lateinit var statsViewModel: StatsViewModel
    lateinit var viewModel: GameViewModel

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences =
            getSharedPreferences(getString(R.string.app_name) + "Data", Context.MODE_PRIVATE)
        val statsCache : StatsCache.All = StatsCache.Base(sharedPreferences)
        viewModel = GameViewModel(
            GameRepository.Base(
                statsCache,
                IntCache.Base(sharedPreferences, "indexKey", 0),
                StringCache.Base(sharedPreferences,"userInputKey", ""),
                ShuffleStrategy.Reverse()
            )
        )
    }
}


