package com.github.cawboyroy.expertcoursestudy.game

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.Matcher
import com.github.cawboyroy.expertcoursestudy.R

class GamePage(word: String) {
    
//region fields
    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.rootLayout))
    private val containerClassTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val shuffledWordUi = ShuffledWordUi(
        text = word,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher)

    private val inputUi = InputUi(
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher)

    private val checkUi = CheckButtonUi(
//        id = R.id.checkButton(),
//        textResId = R.string.check,
//        colorHex = "#E8B931",
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )

    private val skipUi = ButtonUi(
        id = R.id.skipButton,
        textResId = R.string.skip,
        colorHex = "#E8B931",
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )

    private val nextUi = ButtonUi(
        id = R.id.nextButton,
        textResId = R.string.next,
        colorHex = "#14AE5C",
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )
//endregion

    //region asserts
    fun assertInitialState() {
        shuffledWordUi.assertTextVisible()
        inputUi.assertInitialState()
        checkUi.assertVisibleDisabled()
        skipUi.assertVisible()
        nextUi.assertNotVisible()
    }

    fun addInput(text: String) {
        inputUi.addInput(text = text)
    }

    fun assertInsufficientState() {
        shuffledWordUi.assertTextVisible()
        inputUi.assertInsufficientState()
        checkUi.assertVisibleDisabled()
        skipUi.assertVisible()
        nextUi.assertNotVisible()
    }

    fun asserSufficientState() {
        shuffledWordUi.assertTextVisible()
        inputUi.assertSufficientState()
        checkUi.assertVisibleEnabled()
        skipUi.assertVisible()
        nextUi.assertNotVisible()
    }

    fun clickCheck() {
        checkUi.click()
    }

    fun assertCorrectState() {
        shuffledWordUi.assertTextVisible()
        inputUi.assertCorrectState()
        checkUi.assertNotVisible()
        skipUi.assertNotVisible()
        nextUi.assertVisible()
    }

    fun clickNext() {
        nextUi.click()
    }

    fun clickSkip() {
        skipUi.click()
    }

    fun assertIncorrectState() {
        shuffledWordUi.assertTextVisible()
        inputUi.assertIncorrectState()
        checkUi.assertVisibleDisabled()
        skipUi.assertVisible()
        nextUi.assertNotVisible()
    }

    fun removeInputLastLetter() {
        inputUi.removeInputLastLetter()
    }
    //endregion
}