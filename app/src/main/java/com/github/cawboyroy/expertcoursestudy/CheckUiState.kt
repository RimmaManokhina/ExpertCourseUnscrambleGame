package com.github.cawboyroy.expertcoursestudy

import android.view.View
import androidx.appcompat.widget.AppCompatButton

interface CheckUiState {

    fun update(checkButton: AppCompatButton)

    abstract class Abstract(
        private val visible: Int,
        private val enabled: Boolean
    ) : CheckUiState {

        override fun update(checkButton: AppCompatButton) = with(checkButton) {
            visibility = visible
            isEnabled = enabled
        }
    }

    object Disabled : Abstract(View.VISIBLE, false)

    object Enabled : Abstract(View.VISIBLE, true)

    object Invisible : Abstract(View.GONE, false)
}