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
    fun caseNumber1() {
        gamePage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInitialState()

        gamePage.addInput(text = "fact")
        gamePage.assertInsufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInsufficientState()

        gamePage.addInput(text = "s")
        gamePage.assertSufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertSufficientState()

        gamePage.clickCheck()
        gamePage.assertCorrectState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertCorrectState()

        gamePage.clickNext()
        gamePage = GamePage(word = "never".reversed())
        gamePage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInitialState()
    }

    /**
     * UGTC-02 other
     */
    @Test
    fun caseNumber2() {
        gamePage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInitialState()

        gamePage.clickSkip()
        gamePage = GamePage (word = "never".reversed())
        gamePage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInitialState()

        gamePage.addInput(text = "neve")
        gamePage.assertInsufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInsufficientState()

        gamePage.clickSkip()
        gamePage = GamePage (word = "entertain".reversed())
        gamePage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInitialState()

        gamePage.addInput(text = "entertai")
        gamePage.assertInsufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInsufficientState()

        gamePage.addInput(text = "n")
        gamePage.assertSufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertSufficientState()

        gamePage.clickSkip()
        gamePage = GamePage (word = "alligator".reversed())
        gamePage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInitialState()

        gamePage.addInput(text = "alligato")
        gamePage.assertInsufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInsufficientState()

        gamePage.addInput(text = "h")
        gamePage.assertSufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertSufficientState()

        gamePage.clickCheck()
        gamePage.assertIncorrectState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertIncorrectState()

        gamePage.clickSkip()
        gamePage = GamePage (word = "left".reversed())
        gamePage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInitialState()
        //11/
        gamePage.addInput(text = "lef")
        gamePage.assertInsufficientState()
        gamePage.addInput(text = "x")
        gamePage.assertSufficientState()
        gamePage.clickCheck()
        gamePage.assertIncorrectState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertIncorrectState()

        gamePage.removeInputLastLetter()
        gamePage.assertInsufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertInsufficientState()

        gamePage.addInput(text = "t")
        gamePage.assertSufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertSufficientState()

        gamePage.removeInputLastLetter()
        gamePage.assertInsufficientState()
        gamePage.addInput(text = "l")
        gamePage.assertSufficientState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertSufficientState()

        gamePage.clickCheck()
        gamePage.assertIncorrectState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertIncorrectState()
    }
}
