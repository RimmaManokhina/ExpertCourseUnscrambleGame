package com.github.cawboyroy.expertcoursestudy.game

import android.view.KeyEvent
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.cawboyroy.expertcoursestudy.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class InputUi(
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
)  {
    private val inputLayoutId: Int = R.id.inputLayout

    private val layoutInteraction: ViewInteraction = onView(
       allOf(
           isAssignableFrom(TextInputLayout::class.java),
           withId(inputLayoutId),
           containerIdMatcher,
           containerClassTypeMatcher
       )
    )

    private val inputInteraction: ViewInteraction = onView(
        allOf(
            isAssignableFrom(TextInputEditText::class.java),
            withId(R.id.inputEditText),
            withParent(withId(inputLayoutId)),
            withParent(isAssignableFrom(TextInputLayout::class.java))
        )
    )

    private val textInputLayoutErrorEnabledMatcherFalse = TextInputLayoutErrorEnabledMatcher(false)

fun assertInitialState() {
    layoutInteraction
        .check(matches(isEnabled()))
        .check(matches(textInputLayoutErrorEnabledMatcherFalse))
    inputInteraction.check(matches(withText("")))
        }

    fun addInput(text: String) {
        inputInteraction.perform(typeText(text), closeSoftKeyboard())
    }

    fun assertInsufficientState() {
        layoutInteraction
            .check(matches(isEnabled()))
            .check(matches(textInputLayoutErrorEnabledMatcherFalse))
    }

    fun assertSufficientState() {
        layoutInteraction
            .check(matches(isEnabled()))
            .check(matches(textInputLayoutErrorEnabledMatcherFalse))
    }

    fun assertCorrectState() {
        layoutInteraction
            .check(matches(isNotEnabled()))
            .check(matches(textInputLayoutErrorEnabledMatcherFalse))
//        interaction.check(matches(ButtonColorMatcher("#57B05D")))
//            .check(matches(isEnabled()))
//            .check(matches(isNotClickable()))
    }

    fun assertIncorrectState() {
        layoutInteraction
            .check(matches(isEnabled()))
            .check(matches(TextInputLayoutErrorEnabledMatcher(true)))
            .check(matches(TextInputLayoutHasErrorText(R.string.incorrect_message)))
//        interaction.check(matches(ButtonColorMatcher("#C74C46")))
//            .check(matches(isEnabled()))
//            .check(matches(isClickable()))
    }

    fun removeInputLastLetter() {
        inputInteraction.perform(click(), pressKey(KeyEvent.KEYCODE_DEL), closeSoftKeyboard())
    }
}

