package com.github.cawboyroy.expertcoursestudy.game

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.cawboyroy.expertcoursestudy.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher


/**
 * Properties
 * text
 * displayed
 */

class ShuffledWordUi(
    text: String,
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) {
    private val interaction: ViewInteraction = onView(
        allOf(
            containerIdMatcher,
            containerClassTypeMatcher,
            withId(R.id.shuffeledWordTextView),
            withText(text),
            isAssignableFrom(TextView::class.java)
        )
    )
    fun assertTextVisible() {
        interaction.check(matches(isCompletelyDisplayed()))
    }
}
