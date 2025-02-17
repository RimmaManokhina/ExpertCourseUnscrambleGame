package com.github.cawboyroy.expertcoursestudy.game

import android.view.View
import com.github.cawboyroy.expertcoursestudy.stats.NavigateToStats
import com.github.cawboyroy.expertcoursestudy.views.check.UpdateCheckButton
import com.github.cawboyroy.expertcoursestudy.views.input.UpdateInput
import com.github.cawboyroy.expertcoursestudy.views.shuffledWord.UpdateText
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.UpdateVisibility
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.VisibilityUiState

interface GameUiState {

    fun update(
        shuffledWordTextView: UpdateText,
        inputView: View,
        check: UpdateCheckButton,
        skip: UpdateVisibility,
        next: UpdateVisibility
    ) = Unit

    fun navigate(navigateToStats: NavigateToStats) = Unit

    object Empty : GameUiState

    abstract class Abstract(
        private val inputUiState: InputUiState,
        private val checkUiState: CheckUiState
        ) : GameUiState {

            override fun update(
                shuffledWordTextView: UpdateText,
                inputView: UpdateInput,
                check: UpdateCheckButton,
                skip: UpdateVisibility,
                next: UpdateVisibility
            ) {
                inputView.updateUiState(inputUiState)
                check.update(checkUiState)
            }
        }

        data class Initial(
            private val shuffledWord: String,
            private val userInput: String = ""
        ) : Abstract(
            InputUiState.Initial(userInput),
            CheckUiState.Invisible
        ) {
            override fun update(
                shuffledWordTextView: UpdateText,
                inputView: View,
                check: UpdateCheckButton,
                skip: UpdateVisibility,
                next: UpdateVisibility
            ) {
                super.update(shuffledWordTextView, inputView, check, skip, next)
                shuffledWordTextView.update(shuffledWord)
                next.update(VisibilityUiState.Gone)
                skip.update(VisibilityUiState.Visible)
            }
        }

        object Insufficient : Abstract(
            InputUiState.Insufficient,
            CheckUiState.Disabled
        )

        object Sufficient : Abstract(
            InputUiState.Sufficient,
            CheckUiState.Enabled
        )

        object Correct : Abstract(
            InputUiState.Correct,
            CheckUiState.Invisible
        ) {
            override fun update(
                shuffledWordTextView: UpdateText,
                inputView: View,
                check: UpdateCheckButton,
                skip: UpdateVisibility,
                next: UpdateVisibility
            ) {
                super.update(shuffledWordTextView, inputView, check, skip, next)
                next.update(VisibilityUiState.Visible)
                skip.update(VisibilityUiState.Gone)
            }
        }

        object Incorrect : Abstract(
            InputUiState.Incorrect,
            CheckUiState.Disabled
        )
    }