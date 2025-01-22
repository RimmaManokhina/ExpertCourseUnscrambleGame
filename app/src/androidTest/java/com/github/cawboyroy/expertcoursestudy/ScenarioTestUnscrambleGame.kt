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
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

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

        scenarioRule.doWithRecreate { gamePage.assertInitialState() }

        gamePage.addInput(text = "fact")
        scenarioRule.doWithRecreate { gamePage.assertInsufficientState() }

        gamePage.addInput(text = "s")
        scenarioRule.doWithRecreate { gamePage.assertSufficientState() }

        gamePage.clickCheck()
        scenarioRule.doWithRecreate { gamePage.assertCorrectState()}

        gamePage.clickNext()
        gamePage = GamePage(word = "never".reversed())
        scenarioRule.doWithRecreate { gamePage.assertInitialState() }
    }

    /**
     * UGTC-02 other
     */
    @Test
    fun caseNumber2() {

        scenarioRule.doWithRecreate { gamePage.assertInitialState() }

        gamePage.clickSkip()
        gamePage = GamePage (word = "never".reversed())
        scenarioRule.doWithRecreate { gamePage.assertInitialState() }

        gamePage.addInput(text = "neve")
        scenarioRule.doWithRecreate { gamePage.assertInsufficientState() }

        gamePage.clickSkip()
        gamePage = GamePage (word = "entertain".reversed())
        scenarioRule.doWithRecreate { gamePage.assertInitialState() }

        gamePage.addInput(text = "entertai")
        scenarioRule.doWithRecreate { gamePage.assertInsufficientState() }

        gamePage.addInput(text = "n")
        scenarioRule.doWithRecreate { gamePage.assertSufficientState() }

        gamePage.clickSkip()
        gamePage = GamePage (word = "alligator".reversed())
        scenarioRule.doWithRecreate { gamePage.assertInitialState() }

        gamePage.addInput(text = "alligato")
        scenarioRule.doWithRecreate { gamePage.assertInsufficientState() }

        gamePage.addInput(text = "h")
        scenarioRule.doWithRecreate { gamePage.assertSufficientState() }

        gamePage.clickCheck()
        scenarioRule.doWithRecreate { gamePage.assertIncorrectState() }

        gamePage.clickSkip()
        gamePage = GamePage (word = "left".reversed())
        scenarioRule.doWithRecreate { gamePage.assertInitialState() }

        
        //11/
        gamePage.addInput(text = "lef")
        scenarioRule.doWithRecreate { gamePage.assertInsufficientState() }
        gamePage.addInput(text = "x")
        scenarioRule.doWithRecreate { gamePage.assertSufficientState() }
        gamePage.clickCheck()

        scenarioRule.doWithRecreate { gamePage.assertIncorrectState() }

        gamePage.removeInputLastLetter()
        scenarioRule.doWithRecreate { gamePage.assertInsufficientState() }

        gamePage.addInput(text = "t")
        scenarioRule.doWithRecreate { gamePage.assertSufficientState() }
        
        gamePage.removeInputLastLetter()
        scenarioRule.doWithRecreate { gamePage.assertInsufficientState() }
        gamePage.addInput(text = "l")

        scenarioRule.doWithRecreate { gamePage.assertSufficientState() }

        gamePage.clickCheck()
        scenarioRule.doWithRecreate { gamePage.assertIncorrectState() }
    }

    private fun ActivityScenarioRule<*>.doWithRecreate(block: () -> Unit) {
        block.invoke()
        scenario.recreate()
        block.invoke()       
    }
}
