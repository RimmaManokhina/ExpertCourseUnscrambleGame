package com.github.cawboyroy.expertcoursestudy.core

import androidx.annotation.Discouraged
import com.github.cawboyroy.expertcoursestudy.load.RunAsync
import com.github.cawboyroy.expertcoursestudy.load.UiObservable

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

interface MyViewModel {

    interface Async<T : Any> : MyViewModel<T> {

        fun startUpdates(observer: (T) -> Unit)

        fun stopUpdates()
    }

    abstract class Abstract<T : Any> (
        runAsync: RunAsync,
        protected val observable: UiObservable<T>
    ) : Async<T> {

        protected val viewModelScope = CoroutineScope(SupervisorJob() + Discouraged())

        override fun startUpdates(observer: (T) -> Unit) = observable.register()

        override fun stopUpdates() = observable.unregister()
    }
}