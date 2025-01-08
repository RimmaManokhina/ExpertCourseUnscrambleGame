package com.github.cawboyroy.expertcoursestudy

import android.view.View
import com.github.cawboyroy.expertcoursestudy.databinding.ActivityMainBinding

interface GameUiState {

    fun update(binding: ActivityMainBinding)

    /** blueprint из figma идёт в abstract class*/
    abstract class Abstract(
        private val shuffledWordValue: String,
        private val inputUiState: InputUiState,
        private val checkUiState: CheckUiState,
        private val skipVisibility: Int,
        private val nextVisibility: Int
    ) : GameUiState {

        override fun update(binding: ActivityMainBinding) = with(binding) {
            shuffeledWordTextView.text = shuffledWordValue
            inputUiState.update(inputLayout, inputEditText)
            checkUiState.update(checkButton)
            skipButton.visibility = skipVisibility
            nextButton.visibility = nextVisibility
        }
    }

    data class Initial(private val shuffledWord: String
    ) : Abstract(
        shuffledWord,
        InputUiState.Initial,
        CheckUiState.Disabled,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.INVISIBLE
    )

    data class Insufficient(private val shuffledWord: String
    ) : Abstract(
        shuffledWord,
        InputUiState.InSufficient,
        CheckUiState.Disabled,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.INVISIBLE
    )

    data class Sufficient(private val shuffledWord: String
    ) : Abstract(
        shuffledWord,
        InputUiState.Sufficient,
        CheckUiState.Enabled,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.INVISIBLE
    )

    data class Correct(private val shuffledWord: String
    ) : Abstract(
        shuffledWord,
        InputUiState.Correct,
        CheckUiState.Invisible,
        skipVisibility = View.GONE,
        nextVisibility = View.VISIBLE
    )

    data class Incorrect(private val shuffledWord: String
    ) : Abstract(
        shuffledWord,
        InputUiState.Incorrect,
        CheckUiState.Disabled,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.INVISIBLE
    )
}