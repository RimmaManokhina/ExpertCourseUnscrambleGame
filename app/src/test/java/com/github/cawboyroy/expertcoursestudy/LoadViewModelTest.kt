package com.github.cawboyroy.expertcoursestudy

import com.github.cawboyroy.expertcoursestudy.load.LoadViewModel
import com.github.cawboyroy.expertcoursestudy.load.RunAsync
import com.github.cawboyroy.expertcoursestudy.load.UiObservable
import com.github.cawboyroy.expertcoursestudy.load.data.LoadRepository
import com.github.cawboyroy.expertcoursestudy.load.data.Loaded
import com.github.cawboyroy.expertcoursestudy.load.data.NoInternetConnectionException
import com.github.cawboyroy.expertcoursestudy.load.presentation.LoadUiObservable
import com.github.cawboyroy.expertcoursestudy.load.presentation.LoadUiState
import com.github.cawboyroy.expertcoursestudy.views.error.UpdateError
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.UpdateVisibility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class LoadViewModelTest {

    private lateinit var repository: FakeLoadRepository
    private lateinit var observable: FakeLoadUiObservable
    private lateinit var runAsync: FakeRunAsync
    private lateinit var viewModel: LoadViewModel
    private lateinit var fragment: FakeFragment
    private lateinit var clearViewModel: FakeClearViewModel


    @Before
    fun setup() {
        repository = FakeLoadRepository()
        observable = FakeLoadUiObservable()
        runAsync = FakeRunAsync()
        clearViewModel = FakeClearViewModel()
        viewModel = LoadViewModel(
            repository = repository,
            observable = observable,
            runAsync = runAsync,
            clearViewModel = clearViewModel
        )
        fragment = FakeFragment()
    }

    @Test
    fun sameFragment() {

        viewModel.load(isFirstRun = true) // onViewCreated first time
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)

        assertEquals(1, repository.loadCalledCount) // ping repository to get data after ping of uiObservable with progress

        viewModel.startUpdates(observer = fragment) // onResume
        assertEquals(1, observable.registerCalledCount)

        assertEquals(LoadUiState.Progress, fragment.statesList.first()) // give cashed progress ui state to fragment
        assertEquals(1, fragment.statesList.size)

        runAsync.returnResult() // get data from server
        assertEquals(LoadUiState.Success, observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(LoadUiState.Success, fragment.statesList[1])
        assertEquals(2, fragment.statesList.size)
        clearViewModel.assertClearCalled(LoadViewModel::class.java)
    }

    @Test
    fun recreateActivity() {

        repository.expectFailure()

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

        runAsync.returnResult() // providesError to observable
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

    private var exception: Exception? = null

    fun expectFailure() {
        exception = NoInternetConnectionException()
    }

    var loadCalledCount = 0

    override suspend fun tryLoad(): Loaded {
        loadCalledCount++
        exception?.let {
            throw it
        }
        return true
    }

    override suspend fun loadInternal() = Unit
}

private interface FakeLoadUiObservable : FakeUiObservable<LoadUiState>, LoadUiObservable {

    class Base : FakeUiObservable.Abstract<LoadUiState>(), FakeLoadUiObservable
}

interface FakeUiObservable<T : Any> : UiObservable<T> {

    var registerCalledCount: Int
    var unregisterCalledCount: Int
    val postUiStateCalledList: MutableList<T>

    abstract class Abstract<T : Any> : FakeUiObservable<T> {

        private var uiStateCached: T? = null
        private var observerCached: ((T) -> Unit)? = null

        override var registerCalledCount: Int = 0
        override var unregisterCalledCount: Int = 0
        override val postUiStateCalledList: MutableList<T> = mutableListOf()

        override fun register(observer: (T) -> Unit) {
            registerCalledCount++
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached!!)
                uiStateCached = null
            }
        }

        override fun unregister() {
            unregisterCalledCount++
            observerCached = null
        }

        override fun postUiState(uiState: T) {
            postUiStateCalledList.add(uiState)
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached!!.invoke(uiState)
                uiStateCached = null
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class FakeRunAsync : RunAsync {

    private var result: Any? = null
    private var ui: (Any) -> Unit = {}

    override fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    ) = runBlocking {
        result = heavyOperation.invoke()
        ui = uiUpdate as (Any) -> Unit
    }

    override suspend fun <T : Any> handleAsync(
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    ) {
        uiUpdate.invoke(heavyOperation.invoke())
    }

    fun returnResult() {
        ui.invoke(result!!)
    }
}


@Suppress("UNCHECKED_CAST")
class FakeRunAsyncImmediate : RunAsync {

    override fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    ) = runBlocking {
        val result = heavyOperation.invoke()
        uiUpdate.invoke(result)
    }

    override suspend fun <T : Any> handleAsync(
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    ) {
        uiUpdate.invoke(heavyOperation.invoke())
    }
}

private class FakeHandleLoading : HandleLoading {

    var result = false

    override suspend fun handleLoading(block: suspend () -> Boolean): LoadUiState {
        result = block.invoke()
        return FakeLoadUiState
    }
}

private data object FakeLoadUiState : LoadUiState {

    override fun show(
        errorTextView: UpdateError,
        retryButton: UpdateVisibility,
        progressBar: UpdateVisibility
    ) = Unit
}