package com.github.cawboyroy.expertcoursestudy.game.di

import com.github.cawboyroy.expertcoursestudy.di.AbstractProvideViewModel
import com.github.cawboyroy.expertcoursestudy.di.Core
import com.github.cawboyroy.expertcoursestudy.di.Module
import com.github.cawboyroy.expertcoursestudy.di.ProvideViewModel
import com.github.cawboyroy.expertcoursestudy.game.data.GameRepository
import com.github.cawboyroy.expertcoursestudy.game.GameViewModel
import com.github.cawboyroy.expertcoursestudy.game.data.IntCache
import com.github.cawboyroy.expertcoursestudy.game.data.ShuffleStrategy
import com.github.cawboyroy.expertcoursestudy.game.StringCache

/**GameDi*/
class ProvideGameViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, GameViewModel::class.java) {

    override fun module() = GameModule(core)
}

class GameModule(private val core: Core) : Module<GameViewModel> {

    override fun viewModel() =  GameViewModel (
        GameRepository.Base(
            core.statsCache,
            IntCache.Base(core.sharedPreferences, "indexKey", 0),
            StringCache.Base(core.sharedPreferences, "userInputKey", ""),
            ShuffleStrategy.Reverse()
        ),
        core.clearViewModel
    )
}