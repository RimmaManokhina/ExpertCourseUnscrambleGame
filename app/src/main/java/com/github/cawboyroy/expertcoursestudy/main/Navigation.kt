package com.github.cawboyroy.expertcoursestudy.main

import com.github.cawboyroy.expertcoursestudy.core.Screen
import com.github.cawboyroy.expertcoursestudy.game.presentation.GameScreen
import com.github.cawboyroy.expertcoursestudy.game.presentation.NavigateToGame
import com.github.cawboyroy.expertcoursestudy.load.LoadScreen
import com.github.cawboyroy.expertcoursestudy.load.presentation.NavigateToLoad
import com.github.cawboyroy.expertcoursestudy.stats.NavigateToStats
import com.github.cawboyroy.expertcoursestudy.stats.StatsScreen

interface Navigation : NavigateToGame, NavigateToStats, NavigateToLoad {

    fun navigate(screen: Screen)

    override fun navigateToGame() = navigate(GameScreen)

    override fun navigateToStats() = navigate(StatsScreen)

    override fun navigateToLoad() = navigate(LoadScreen)

}