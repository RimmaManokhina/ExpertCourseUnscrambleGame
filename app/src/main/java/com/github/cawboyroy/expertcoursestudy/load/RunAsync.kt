package com.github.cawboyroy.expertcoursestudy.load

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RunAsync {

    fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    )

    suspend fun <T : Any> handleAsync(
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    )

    class Base : RunAsync {

        override fun <T : Any> handleAsync(
            coroutineScope: CoroutineScope,
            heavyOperation: suspend () -> T,
            uiUpdate: (T) -> Unit
        ) {
            coroutineScope.launch(Dispatchers.IO) {
                val result = heavyOperation.invoke()
                withContext(Dispatchers.Main) {
                    uiUpdate.invoke(result)
                }
            }
        }

        override suspend fun <T : Any> handleAsync(
            heavyOperation: suspend () -> T,
            uiUpdate: (T) -> Unit
        ) {
            withContext(Dispatchers.IO) {
                val result = heavyOperation.invoke()
                withContext(Dispatchers.Main) {
                    uiUpdate.invoke(result)
                }
            }
        }
    }
}