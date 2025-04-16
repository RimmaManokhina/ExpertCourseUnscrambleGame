package com.github.cawboyroy.expertcoursestudy.load

interface UiObservable<T : Any> {

    fun register(observer: (T) -> Unit)

    fun unregister()

    fun postUiState(uiState: T)

    abstract class Abstract<T : Any> : UiObservable<T> {

        private var uiStateCached: T? = null
        private var observerCached: ((T) -> Unit)? = null

        override fun register(observer: (T) -> Unit) {
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached!!)
                uiStateCached = null
            }
        }

        override fun unregister() {
            observerCached = null
        }

        override fun postUiState(uiState: T) {
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached!!.invoke(uiState)
                uiStateCached = null
            }
        }
    }
}
        /* 1. register aka fragment onResume
        2. some time lasted
        3. postUiState -> immediately update ui (no caching)*/
        /* 1. register aka fragment onResume
        2. some time lasted
        3. unregister aga fragment onPause
        4. some time lasted
        5. postUiState : cache ui state and wait till register aka onResume new fragment
        6. register new fragment aka onResume:  immediately update ui (clear caching) */