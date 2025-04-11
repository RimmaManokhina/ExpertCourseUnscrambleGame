package com.github.cawboyroy.expertcoursestudy.game

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.cawboyroy.expertcoursestudy.AbstractButtonUi
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

/**
 * Properties
 * displayed
 */
class ButtonUi(
    id: Int,
    colorHex: String,
    @StringRes textResId: Int,
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) : AbstractButtonUi(
    onView(
        allOf(
            withId(id),
            ButtonColorMatcher(colorHex),
            withText(textResId),
            containerIdMatcher,
            containerClassTypeMatcher,
            isAssignableFrom(AppCompatButton::class.java)
        )
    )
)