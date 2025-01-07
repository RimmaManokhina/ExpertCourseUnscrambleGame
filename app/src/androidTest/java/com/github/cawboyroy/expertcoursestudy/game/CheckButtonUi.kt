package com.github.cawboyroy.expertcoursestudy.game

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
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
)  : AbstractButtonUi(
    onView(
        allOf(
            containerIdMatcher,
            containerClassTypeMatcher,
            isAssignableFrom(AppCompatButton::class.java),
            withId(R.id.checkButton)

            //withText(R.string.check)
//                    Change:
//
//                    onView(withText(R.id.user_name)).perform(click());
//                      with
//                      onView(withId(R.id.user_name)).perform(click());
        )
    )
) {
    fun assertVisibleDisabled() {
        interaction
            .check(matches(ButtonColorMatcher("#B3B3B3")))
            .check(matches(isNotEnabled()))
            .check(matches(isClickable()))
            .check(matches(isDisplayed()))
    }

    fun assertVisibleEnabled() {
        interaction
            .check(matches(ButtonColorMatcher("#E8B931")))
            .check(matches(isEnabled()))
            .check(matches(isClickable()))
            .check(matches(isDisplayed()))
    }
}


