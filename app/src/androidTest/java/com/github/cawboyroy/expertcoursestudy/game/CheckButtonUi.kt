package com.github.cawboyroy.expertcoursestudy.game

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.cawboyroy.expertcoursestudy.AbstractButtonUi
import com.github.cawboyroy.expertcoursestudy.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

/**
 * Properties
 * color
 * enabled
 * clickable
 * displayed
 */
class CheckButtonUi(
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) : AbstractButtonUi(
    onView(
        allOf(
            withId(R.id.checkButton),
            withText(R.string.check),
            isAssignableFrom(AppCompatButton::class.java),
            ButtonColorMatcher("#5D8AFF"),
            containerIdMatcher,
            containerClassTypeMatcher
        )
    )
) {

    fun assertVisibleDisabled() {
        interaction.check(matches(isNotEnabled()))
            .check(matches(isCompletelyDisplayed()))
    }

    fun assertVisibleEnabled() {
        interaction.check(matches(isEnabled()))
            .check(matches(isCompletelyDisplayed()))
    }

    override fun assertTillVisible() {}

    override fun waitTillDoesNotExist() {}
}