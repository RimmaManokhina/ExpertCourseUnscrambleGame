package com.github.cawboyroy.expertcoursestudy.stats

import android.view.View
import android.widget.FrameLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.github.cawboyroy.expertcoursestudy.R
import com.github.cawboyroy.expertcoursestudy.game.ButtonUi
import org.hamcrest.Matcher

class StatsPage(skips: Int, fails: Int, corrects: Int) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.statsLayout))
    private val containerClassTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(FrameLayout::class.java))

    private val statsUi = StatsUi(
        skips = skips,
        fails = fails,
        corrects = corrects,
        containerIdMatcher,
        containerClassTypeMatcher
    )

    private val newGameUi = ButtonUi(
        R.id.newGameButton,
        "#F251D5",
        R.string.new_game,
        containerIdMatcher,
        containerClassTypeMatcher
    )

    fun assertInitialState() {
        statsUi.assertVisible()
    }

    fun clickNewGame() {
        newGameUi.click()
    }
}


