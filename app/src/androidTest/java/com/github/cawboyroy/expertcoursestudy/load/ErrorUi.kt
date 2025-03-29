package com.github.cawboyroy.expertcoursestudy.load

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.cawboyroy.expertcoursestudy.AbstractButtonUi
import com.github.cawboyroy.expertcoursestudy.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ErrorUi(
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>,
    interaction: ViewInteraction = onView(
        allOf(
            withId(R.id.errorTextView),
            withText(R.string.no_internet_connection),
            isAssignableFrom(TextView::class.java),
            containerIdMatcher,
            containerClassTypeMatcher
        )
    )
) : AbstractButtonUi(interaction) {

    override fun assertVisible() {}

    override fun assertNotVisible() {}

    override fun assertTillVisible() {}

    override fun waitTillDoesNotExist() {}

}

