package com.github.cawboyroy.expertcoursestudy.load

import org.junit.Test
import org.junit.Assert.assertEquals

class LoadViewModelTest {

    @Test
    fun sameFragment() {

        val repository = FakeLoadRepository()
        repository.expectResult(LoadResult.Success)
        val observable = FakeUiObservable()
        val viewModel = LoadViewModel(
            repository = repository,
            observable = observable
        )
        val fragment = FakeFragment()

        viewModel.load(isFirstRun = true) // onViewCreated first time
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount) // ping repository to get data after ping of uiObservable with progress

        viewModel.startUpdates(observer = fragment) // onResume
        assertEquals(1, observable.registerCalledCount)

        assertEquals(LoadUiState.Progress, fragment.statesList.first()) // give cashed progress ui state to fragment
        assertEquals(1, fragment.statesList.size)

        repository.returnResult() // get data from server
        assertEquals(LoadUiState.Success, observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(LoadUiState.Success, fragment.statesList[1])
        assertEquals(2, fragment.statesList.size)
    }

    @Test
    fun recreateActivity() {
        val repository = FakeLoadRepository()
        repository.expectResult(LoadResult.Error(message = "no internet"))
        val observable = FakeUiObservable()
        val viewModel = LoadViewModel(
            repository = repository,
            observable = observable
        )
        val fragment = FakeFragment()

        viewModel.load(isFirstRun = true) // onViewCreated first time
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount) // ping repository to get data after ping of uiObservable with progress

        viewModel.startUpdates(observer = fragment) // onResume
        assertEquals(1, observable.registerCalledCount)

        assertEquals(LoadUiState.Progress, fragment.statesList.first()) // give cashed progress ui state to fragment
        assertEquals(1, fragment.statesList.size)

        viewModel.stopUpdates() // onPause and activity death (aka onStop, onDestroy)
        assertEquals(1, observable.unregisterCalledCount)

        repository.returnResult() // providesError to observable
        assertEquals(1, fragment.statesList.size)
        assertEquals(LoadUiState.Error(message = "no internet"), observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)

        val newInstanceOfFragment = FakeFragment() // new instance of Fragment after activity recreate

        viewModel.load(isFirstRun = false) // onViewCreated after activity recreate
        assertEquals(1, repository.loadCalledCount)
        assertEquals(2, observable.postUiStateCalledList.size)

        viewModel.startUpdates(observer = newInstanceOfFragment) // onResume ofter activity recreate
        assertEquals(2, observable.registerCalledCount)

        assertEquals(LoadUiState.Error(message = "no internet"),
            newInstanceOfFragment.statesList.first())
        assertEquals(1, newInstanceOfFragment.statesList.size)
    }
}

private class FakeFragment : (LoadUiState) -> Unit {

    val statesList = mutableListOf<LoadUiState>()

    override fun invoke(p1: LoadUiState) {
        statesList.add(p1)
    }
}

private class FakeLoadRepository : LoadRepository {

    private var loadResult: LoadResult? = null
    private var loadResultCalledBack: (LoadResult) -> Unit = {}

    fun expectResult(loadResult: LoadResult) {
        this.loadResult = loadResult
    }

    var loadCalledCount = 0

    override fun load(resultCallBack: (LoadResult) -> Unit) {
        loadCalledCount++
        loadResultCallBack = resultCallBack
    }

    fun returnResult() {
        loadResultCalledBack.invoke(loadResult!!)
    }
}

    private class FakeUiObservable : UiObservable {

        private var uiStateCached: LoadUiState? = null
        private var observerCached: ((LoadUiState) -> Unit)? = null

        var registerCalledCount = 0

        override fun register(observer: (LoadUiState) -> Unit) {
            registerCalledCount++
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached!!)
                uiStateCached = null
            }
        }

        var unregisterCalledCount = 0

        override fun unregister() {
            unregisterCalledCount++
            observerCached = null
        }

        val postUiStateCalledList = mutableListOf<LoadUiState>()

        override fun postUiState(uiState: LoadUiState) {
            postUiStateCalledList.add(uiState)
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached!!.invoke(uiState)
                uiStateCached = null
            }
        }
    }
