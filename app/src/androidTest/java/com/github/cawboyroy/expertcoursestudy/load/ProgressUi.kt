package com.github.cawboyroy.expertcoursestudy.load

import android.view.View
import android.widget.ProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.cawboyroy.expertcoursestudy.AbstractButtonUi
import com.github.cawboyroy.expertcoursestudy.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ProgressUi(
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>,
    interaction: ViewInteraction = onView(
        allOf(
        withId(R.id.progressBar),
        isAssignableFrom(ProgressBar::class.java),
        containerIdMatcher,
        containerClassTypeMatcher
        )
    )
) : AbstractButtonUi(interaction) {

    override fun assertVisible() {
        onView(isRoot()).perform(waitTillDisplayed(R.id.progressBar, 4000))
    }

    override fun assertNotVisible() {
        onView(isRoot()).perform(waitTillDoesntExist(R.id.progressBar, 4000))
    }
}
