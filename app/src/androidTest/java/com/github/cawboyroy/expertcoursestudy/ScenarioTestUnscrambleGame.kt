package com.github.cawboyroy.expertcoursestudy

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.cawboyroy.expertcoursestudy.game.GamePage

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ScenarioTestUnscrambleGame(

    private var gamePage: GamePage) {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    //private lateinit var gamePage: GamePage("facts".reversed())

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

        gamePage.addInput(text = "fact")
        gamePage.assertInsufficientState()

        gamePage.addInput(text = "s")
        gamePage.asserSufficientState()

        gamePage.clickCheck()
        gamePage.assertCorrectState()

        gamePage.clickNext()
        gamePage = GamePage(word = "never".reversed())
        gamePage.assertInitialState()
    }

    /**
     * UGTC-02 other
     */
    @Test
    fun caseNumber2() {
        gamePage.assertInitialState()

        gamePage.clickSkip()
        gamePage= GamePage (word = "never".reversed())
        gamePage.assertInitialState()

        gamePage.addInput(text = "neve")
        gamePage.assertInsufficientState()

        gamePage.clickSkip()
        gamePage= GamePage (word = "entertain".reversed())
        gamePage.assertInitialState()

        gamePage.addInput(text = "entertai")
        gamePage.asserSufficientState()

        gamePage.clickSkip()
        gamePage= GamePage (word = "alligator".reversed())
        gamePage.assertInitialState()

        gamePage.addInput(text = "alligato")
        gamePage.assertInsufficientState()

        gamePage.addInput(text = "h")
        gamePage.asserSufficientState()

        gamePage.clickCheck()
        gamePage.assertIncorrectState()

        gamePage.clickSkip()
        gamePage= GamePage (word = "left".reversed())
        gamePage.assertInitialState()

        //11/
        gamePage.addInput(text = "lef")
        gamePage.assertInsufficientState()

        gamePage.addInput(text = "x")
        gamePage.asserSufficientState()

        gamePage.clickCheck()
        gamePage.assertIncorrectState()

        gamePage.removeInputLastLetter()
        gamePage.assertInsufficientState()

        gamePage.addInput(text = "t")
        gamePage.asserSufficientState()

        gamePage.removeInputLastLetter()
        gamePage.assertInsufficientState()

        gamePage.addInput(text = "l")
        gamePage.asserSufficientState()

        gamePage.clickCheck()
        gamePage.assertIncorrectState()
    }


}



//"facts",
//"never",
//"entertain",
//"alligator",
//"left",
//"handle",
//"panda",
//"effort",
//"January",
//"extra",
//"camera",
//"plant",
//"every",
//"exit",
//"spelling",
//"hello",
//"clever"
