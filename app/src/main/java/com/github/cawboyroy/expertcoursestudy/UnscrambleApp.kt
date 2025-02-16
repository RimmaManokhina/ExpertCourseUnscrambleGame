package com.github.cawboyroy.expertcoursestudy

import android.app.Application
import android.content.Context
import com.github.cawboyroy.expertcoursestudy.game.GameRepository
import com.github.cawboyroy.expertcoursestudy.game.GameViewModel
import com.github.cawboyroy.expertcoursestudy.game.IntCache
import com.github.cawboyroy.expertcoursestudy.game.ShuffleStrategy
import com.github.cawboyroy.expertcoursestudy.game.StringCache

class UnscrambleApp : Application() {

    lateinit var viewModel: GameViewModel

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences(getString(R.string.app_name) + "Data", Context.MODE_PRIVATE)
        viewModel = GameViewModel(
            GameRepository.Base(
                IntCache.Base(sharedPreferences, "indexKey", 0),
                StringCache.Base(sharedPreferences,"userInputKey", ""),
                ShuffleStrategy.Reverse()
            )
        )
    }
}


