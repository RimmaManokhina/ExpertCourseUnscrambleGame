package com.github.cawboyroy.expertcoursestudy.game.di

import com.github.cawboyroy.expertcoursestudy.load.RunAsync
import com.github.cawboyroy.expertcoursestudy.load.UiObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface MyViewModel {

    interface Async<T : Any> : MyViewModel {

        fun startUpdates(observer: (T) -> Unit)

        fun stopUpdates()
    }

    abstract class Abstract<T : Any>(
        private val runAsync: RunAsync,
        protected val observable: UiObservable<T>
    ) : Async<T> {

        private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

        protected fun <T : Any> runAsync(heavy: suspend () -> T, ui: (T) -> Unit) {
            runAsync.handleAsync(viewModelScope, heavy, ui)
        }

        protected suspend fun <T : Any> runAsyncInternal(heavy: suspend () -> T, ui: (T) -> Unit) {
            runAsync.handleAsync(heavy, ui)
        }

        override fun startUpdates(observer: (T) -> Unit) = observable.register(observer)

        override fun stopUpdates() = observable.unregister()
    }
}