package com.github.cawboyroy.expertcoursestudy.load.presentation

import com.github.cawboyroy.expertcoursestudy.R
import com.github.cawboyroy.expertcoursestudy.game.NavigateToGame
import com.github.cawboyroy.expertcoursestudy.views.error.ErrorUiState
import com.github.cawboyroy.expertcoursestudy.views.error.UpdateError
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.UpdateVisibility
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.VisibilityUiState

interface LoadUiState {

    fun show(
        errorTextView: UpdateError,
        retryButton: UpdateVisibility,
        progressBar: UpdateVisibility
    )

    fun navigate(navigateToGame: NavigateToGame) = Unit

    abstract class Abstract(
        private val errorUiState: ErrorUiState,
        private val retryUiState: VisibilityUiState,
        private val progressUiState: VisibilityUiState,
    ) : LoadUiState {
        override fun show(
            errorTextView: UpdateError,
            retryButton: UpdateVisibility,
            progressBar: UpdateVisibility,
        ) {
            errorTextView.update(errorUiState)
            retryButton.update(retryUiState)
            progressBar.update(progressUiState)
        }
    }

    object Progress : Abstract (
        ErrorUiState.Hide,
        VisibilityUiState.Gone,
        VisibilityUiState.Visible)

    object Success : Abstract (
        ErrorUiState.Hide,
        VisibilityUiState.Gone,
        VisibilityUiState.Gone) {

        override fun navigate(navigateToGame: NavigateToGame) {
            navigateToGame.navigateToGame()
        }
    }

    data class Error(private val message: String
    ) : Abstract(
        ErrorUiState.Show(R.string.no_internet_connection),
        VisibilityUiState.Visible,
        VisibilityUiState.Gone)
}