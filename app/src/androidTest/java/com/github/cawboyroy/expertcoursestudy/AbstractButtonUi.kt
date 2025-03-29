package com.github.cawboyroy.expertcoursestudy

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.CoreMatchers.not

abstract class AbstractButtonUi(
    open val interaction: ViewInteraction
) {

    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }

    open fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

    open fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }

    open fun assertTillVisible() {}

    open fun waitTillDoesNotExist() {}
}