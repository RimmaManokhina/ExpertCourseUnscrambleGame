package com.github.cawboyroy.expertcoursestudy.load

import com.github.cawboyroy.expertcoursestudy.R
import com.github.cawboyroy.expertcoursestudy.game.presentation.NavigateToGame
import com.github.cawboyroy.expertcoursestudy.views.error.ErrorUiState
import com.github.cawboyroy.expertcoursestudy.views.error.UpdateError
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.UpdateVisibility
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.VisibilityUiState

interface LoadViewModel {

    fun show(
        errorTextView: UpdateError,
        retryButton: UpdateVisibility,
        progressBar: UpdateVisibility
    )

    fun navigate(navigateToGame: NavigateToGame) = Unit

    fun handle(observable: UiObservable<LoadViewModel>) = observable.postUiState(this)

    abstract class Abstract(
        private val errorUiState: ErrorUiState,
        private val retryUiState: VisibilityUiState,
        private val progressUiState: VisibilityUiState
    ) : LoadViewModel {
        override fun show(
            errorTextView: UpdateError,
            retryButton: UpdateVisibility,
            progressBar: UpdateVisibility
        ) {
            errorTextView.update(errorUiState)
            retryButton.update(retryUiState)
            progressBar.update(progressUiState)
        }
    }

    object Progress : Abstract(ErrorUiState.Hide, VisibilityUiState.Gone, VisibilityUiState.Visible)

    object Success : Abstract(ErrorUiState.Hide, VisibilityUiState.Gone, VisibilityUiState.Gone) {

        override fun navigate(navigateToGame: NavigateToGame) = navigateToGame.navigateToGame()
    }

    data object Waiting : LoadViewModel {

        override fun show(
            errorTextView: UpdateError,
            retryButton: UpdateVisibility,
            progressBar: UpdateVisibility
        ) = Unit

        override fun handle(observable: UiObservable<LoadViewModel>) = Unit
    }

    data class ErrorRes(val messageId: Int = R.string.no_internet_connection) : Abstract(
        ErrorUiState.Show(messageId),
        VisibilityUiState.Visible,
        VisibilityUiState.Gone,
    )

    data class Error(private val message: String) : Abstract(
        ErrorUiState.ShowRes(message),
        VisibilityUiState.Visible,
        VisibilityUiState.Gone,
    )
}