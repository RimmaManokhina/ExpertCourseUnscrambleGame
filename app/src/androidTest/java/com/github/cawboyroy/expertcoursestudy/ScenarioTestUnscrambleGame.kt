package com.github.cawboyroy.expertcoursestudy

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.cawboyroy.expertcoursestudy.game.GamePage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ScenarioTestUnscrambleGame {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var gamePage: GamePage

    @Before
    fun setup() {
        gamePage = GamePage(word = "facts".reversed())
    }

    /**
     * Unscramble game test case
     * UGTC-01 happy pass
     */
    @Test
    fun caseNumber1() = with(GamePage(word = "facts".reversed())) {
        assertInitialState()
        activityScenarioRule.scenario.recreate()

        assertInitialState()
        addInput(text = "fact")
        assertInsufficientState()
        activityScenarioRule.scenario.recreate()

        assertInsufficientState()
        addInput(text = "s")
        assertSufficientState()
        activityScenarioRule.scenario.recreate()

        assertSufficientState()
        clickCheck()
        assertCorrectState()
        activityScenarioRule.scenario.recreate()

        assertCorrectState()
        clickNext()
        GamePage(word = "never".reversed())
        assertInitialState()
        activityScenarioRule.scenario.recreate()

        assertInitialState()
    }

    /**
     * UGTC-02 other
     */
    @Test
    fun caseNumber2() = with(GamePage(word = "facts".reversed())) {
        assertInitialState()

        clickSkip()
        GamePage (word = "never".reversed())
        assertInitialState()

        addInput(text = "neve")
        assertInsufficientState()

        clickSkip()
        GamePage (word = "entertain".reversed())
        assertInitialState()

        addInput(text = "entertai")
        assertSufficientState()

        clickSkip()
        GamePage (word = "alligator".reversed())
        assertInitialState()

        addInput(text = "alligato")
        assertInsufficientState()

        addInput(text = "h")
        assertSufficientState()

        clickCheck()
        assertIncorrectState()

        clickSkip()
        GamePage (word = "left".reversed())
        assertInitialState()
        //11/
        addInput(text = "lef")
        assertInsufficientState()

        addInput(text = "x")
        assertSufficientState()

        clickCheck()
        assertIncorrectState()

        removeInputLastLetter()
        assertInsufficientState()

        addInput(text = "t")
        assertSufficientState()

        removeInputLastLetter()
        assertInsufficientState()

        addInput(text = "l")
        assertSufficientState()

        clickCheck()
        assertIncorrectState()
    }
}
