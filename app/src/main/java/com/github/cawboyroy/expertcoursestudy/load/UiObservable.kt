package com.github.cawboyroy.expertcoursestudy.load

interface UiObservable {

    fun register (observer:  (LoadUiState) -> Unit)

    fun unregister()

    fun postUiState(uiState: LoadUiState)

    class Base : UiObservable {

        private var uiStateCached: LoadUiState? = null //progress, success, fail
        private var observerCached: ((LoadUiState) -> Unit)? = null // aka fragment

        override fun register(observer: (LoadUiState) -> Unit) { // onResume
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached!!) //update ui
                uiStateCached = null
            }
        }

        override fun unregister() { // onPause
            observerCached = null
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

        override fun postUiState(uiState: LoadUiState) { // pinged by ViewModel asynchronously
            if (observerCached == null) { // onPause was called, but onResume still not called
                uiStateCached = uiState // save ui state tii new fragment become onResume
            } else {
                observerCached!!.invoke(uiState) //update ui after onResume and till onPause
                uiStateCached = null
            }
        }
    }
}