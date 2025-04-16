package com.github.cawboyroy.expertcoursestudy.stats

import com.github.cawboyroy.expertcoursestudy.game.di.ClearViewModel
import com.github.cawboyroy.expertcoursestudy.game.di.MyViewModel
import com.github.cawboyroy.expertcoursestudy.stats.data.StatsRepository

class StatsViewModel(
    private val repository: StatsRepository,
    private val clearViewModel: ClearViewModel
) : MyViewModel {

    fun init(isFirstRun: Boolean): StatsUiState {
        return if (isFirstRun) {
            val (skips, corrects, fails) = repository.stats()
            repository.clear()
            StatsUiState.Base(skips, corrects, fails)
        } else {
            StatsUiState.Empty
        }
    }

    fun clear() {
        clearViewModel.clear(StatsViewModel::class.java)
    }
}