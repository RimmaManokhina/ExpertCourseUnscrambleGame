package com.github.cawboyroy.expertcoursestudy.stats

import java.io.Serializable

interface StatsUiState : Serializable {

    fun show(statsTextView: UpdateStats)

    data class Base (
        private val skips: Int,
        private val fails: Int,
        private val corrects: Int
    ) : StatsUiState {

        override fun show(statsTextView: UpdateStats) =
            statsTextView.update(skips, fails, corrects)
    }

    object Empty : StatsUiState {
        override fun show(statsTextView: UpdateStats) = Unit
    }
}
