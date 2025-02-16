package com.github.cawboyroy.expertcoursestudy.views.stats

import java.io.Serializable

interface StatsUiState : Serializable {

    fun show(statsTextView: UpdateStats)

    class Base (
        private val skips: Int,
        private val fails: Int,
        private val corrects: Int
    ) : StatsUiState {

        override fun show(statsTextView: UpdateStats) =
            statsTextView.updateState(skips, fails, corrects)
    }
}
