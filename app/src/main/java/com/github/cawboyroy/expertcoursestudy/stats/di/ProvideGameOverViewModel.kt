package com.github.cawboyroy.expertcoursestudy.stats.di

import com.github.cawboyroy.expertcoursestudy.game.di.AbstractProvideViewModel
import com.github.cawboyroy.expertcoursestudy.core.Core
import com.github.cawboyroy.expertcoursestudy.game.di.Module
import com.github.cawboyroy.expertcoursestudy.game.di.ProvideViewModel
import com.github.cawboyroy.expertcoursestudy.stats.data.StatsRepository
import com.github.cawboyroy.expertcoursestudy.stats.StatsViewModel

class ProvideGameOverViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, viewModelClass = StatsViewModel::class.java) {

    override fun module() = GameOverModule(core)
}

class GameOverModule(
    private val core: Core
) : Module<StatsViewModel> {

    override fun viewModel() = StatsViewModel(StatsRepository.Base(core.statsCache), core.clearViewModel)
}