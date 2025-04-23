package com.github.cawboyroy.expertcoursestudy.game.di

import com.github.cawboyroy.expertcoursestudy.core.Core
import com.github.cawboyroy.expertcoursestudy.game.data.GameRepository
import com.github.cawboyroy.expertcoursestudy.game.data.IntCache
import com.github.cawboyroy.expertcoursestudy.game.presentation.GameViewModel
import com.github.cawboyroy.expertcoursestudy.game.data.ShuffleStrategy
import com.github.cawboyroy.expertcoursestudy.game.data.StringCache
import com.github.cawboyroy.expertcoursestudy.game.presentation.GameObservable
import com.github.cawboyroy.expertcoursestudy.load.data.cache.WordsCacheDataSource

/**GameDi*/
class ProvideGameViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, GameViewModel::class.java) {

    override fun module() = GameModule(core)
}

class GameModule(private val core: Core) : Module<GameViewModel> {

    override fun viewModel() = GameViewModel(
        core.runAsync,
        GameObservable.Base(),
        if (core.runUiTests)
            GameRepository.Fake(
                core.statsCache,
                IntCache.Base(core.sharedPreferences, "indexKey", 0),
                StringCache.Base(core.sharedPreferences, "userInputKey", ""),
                ShuffleStrategy.Reverse()
            )
        else
            GameRepository.Base(
                core.wordsSize,
                WordsCacheDataSource.Base(core.dao()),
                core.statsCache,
                core.indexCache,
                StringCache.Base(core.sharedPreferences, "userInputKey", ""),
                ShuffleStrategy.Base()
            ),
        core.clearViewModel,
    )
}