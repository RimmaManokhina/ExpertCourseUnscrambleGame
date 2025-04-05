package com.github.cawboyroy.expertcoursestudy.load

import com.github.cawboyroy.expertcoursestudy.di.MyViewModel
import com.github.cawboyroy.expertcoursestudy.game.GameUiState

class LoadViewModel (
    private val repository: LoadRepository,
    private val observable: UiObservable
) : MyViewModel {

    fun load(isFirstRun: Boolean = true) {
        if(isFirstRun) {
            observable.postUiState(LoadUiState.Progress)
            repository.load {
                observable.postUiState(
                if (it.isSuccessful())
                    LoadUiState.Success
                else
                    LoadUiState.Error(it.message())
                    )
            }
        }
    }

    fun startUpdates(observer: (LoadUiState) -> Unit) = observable.register(observer)

    fun stopUpdates() = observable.unregister()

    fun handleUserInput(text: String): GameUiState { TODO("Not yet implemented")
    }
}