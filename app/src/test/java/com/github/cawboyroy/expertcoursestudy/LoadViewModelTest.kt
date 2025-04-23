package com.github.cawboyroy.expertcoursestudy

import com.github.cawboyroy.expertcoursestudy.load.HandleLoading
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

    private lateinit var handleLoading: FakeHandleLoading
    private lateinit var repository: FakeLoadRepository
    private lateinit var observable: FakeLoadUiObservable
    private lateinit var runAsync: FakeRunAsync
    private lateinit var viewModel: LoadViewModel
    private lateinit var fragment: FakeFragment

    @Before
    fun setup() {
        repository = FakeLoadRepository()
        observable = FakeLoadUiObservable.Base()
        runAsync = FakeRunAsync()
        handleLoading = FakeHandleLoading()
        viewModel = LoadViewModel(
            handleLoading, repository, observable, runAsync
        )
        fragment = FakeFragment()
    }

    @Test
    fun firstRun() {
        viewModel.load(isFirstRun = true)
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)

        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment)
        assertEquals(1, observable.registerCalledCount)

        assertEquals(
            LoadUiState.Progress,
            fragment.statesList.first()
        )
        assertEquals(1, fragment.statesList.size)

        runAsync.returnResult()
        assertEquals(FakeLoadUiState, observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(FakeLoadUiState, fragment.statesList[1])
        assertEquals(2, fragment.statesList.size)
    }

    @Test
    fun processDeath() {
        viewModel.load(isFirstRun = true)
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)

        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment)
        assertEquals(1, observable.registerCalledCount)

        assertEquals(
            LoadUiState.Progress,
            fragment.statesList.first()
        )
        assertEquals(1, fragment.statesList.size)

        viewModel = LoadViewModel(
            handleLoading, repository, observable, runAsync
        )

        viewModel.load(isFirstRun = false)
        runAsync.returnResult()

        assertEquals(FakeLoadUiState, observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(FakeLoadUiState, fragment.statesList[1])
        assertEquals(2, fragment.statesList.size)
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
        override val postUiStateCalledList: MutableList<T> = MutableListOf()

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