package com.github.cawboyroy.expertcoursestudy.load

import com.github.cawboyroy.expertcoursestudy.game.di.ClearViewModel
import com.github.cawboyroy.expertcoursestudy.game.di.MyViewModel
import com.github.cawboyroy.expertcoursestudy.load.data.LoadRepository
import com.github.cawboyroy.expertcoursestudy.load.data.cloud.HandleError
import com.github.cawboyroy.expertcoursestudy.load.presentation.LoadUiObservable
import com.github.cawboyroy.expertcoursestudy.load.presentation.LoadUiState


class LoadViewModel(
    private val handleLoading: HandleLoading,
    private val repository: LoadRepository,
    observable: LoadUiObservable,
    runAsync: RunAsync,
) : MyViewModel.Abstract<LoadUiState>(runAsync, observable) {

    private val handleProcessDeath = HandleProcessDeath()

    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            handleProcessDeath.reset()
            observable.postUiState(LoadUiState.Progress)
            loadInner()
        } else if (handleProcessDeath.deathHappened()) {
            handleProcessDeath.reset()
            loadInner()
        }
    }

    private fun loadInner() {
        runAsync({ handleLoading.handleLoading(repository::tryLoad) }) { loadingResult ->
            loadingResult.handle(observable)
        }
    }

    suspend fun loadInternal() = runAsyncInternal({
        handleLoading.handleLoading {
            repository.loadInternal()
            true
        }
    }) {
        it.handle(observable)
    }
}

interface HandleLoading {

    suspend fun handleLoading(block: suspend () -> Boolean): LoadUiState

    class Base(
        private val clearViewModel: ClearViewModel,
        private val handleError: HandleError<Int>
    ) : HandleLoading {

        override suspend fun handleLoading(block: suspend () -> Boolean) = try {
            val loaded = block.invoke()
            if (loaded) {
                clearViewModel.clear(LoadViewModel::class.java)
                LoadUiState.Success
            } else
                LoadUiState.Waiting
        } catch (e: Exception) {
            val resource = handleError.handle(e)
            LoadUiState.ErrorRes(resource)
        }
    }
}

class HandleProcessDeath {

    private var deathHappened = true

    fun reset() {
        deathHappened = false
    }

    fun deathHappened() = deathHappened
}