package com.github.cawboyroy.expertcoursestudy.game.di

import com.github.cawboyroy.expertcoursestudy.game.data.GameRepository
import com.github.cawboyroy.expertcoursestudy.game.presentation.GameViewModel
import com.github.cawboyroy.expertcoursestudy.game.data.IntCache
import com.github.cawboyroy.expertcoursestudy.game.data.ShuffleStrategy
import com.github.cawboyroy.expertcoursestudy.game.data.StringCache

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