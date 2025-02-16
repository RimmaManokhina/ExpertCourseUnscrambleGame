package com.github.cawboyroy.expertcoursestudy.main

import com.github.cawboyroy.expertcoursestudy.game.GameScreen
import com.github.cawboyroy.expertcoursestudy.game.NavigateToGame
import com.github.cawboyroy.expertcoursestudy.stats.NavigateToStats
import com.github.cawboyroy.expertcoursestudy.stats.StatsScreen

interface Navigation : NavigateToGame, NavigateToStats {

    fun navigate(screen: Screen)

    override fun navigateToGame() = navigate(GameScreen)

    override fun navigateToStats() = navigate(StatsScreen)
}