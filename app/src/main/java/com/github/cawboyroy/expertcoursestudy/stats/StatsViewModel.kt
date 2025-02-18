package com.github.cawboyroy.expertcoursestudy.stats

import com.github.cawboyroy.expertcoursestudy.StatsRepository

class StatsViewModel(private val repository: StatsRepository) {

    fun init(isFirstRun: Boolean): StatsUiState {
        return if (isFirstRun) {
            val (skips, fails, corrects) = repository.stats()
            repository.clear()
            StatsUiState.Base(skips, fails, corrects)
        } else {
            StatsUiState.Empty
        }
    }
}
