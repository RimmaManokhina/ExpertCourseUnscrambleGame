package com.github.cawboyroy.expertcoursestudy.game.presentation

import android.view.View
import com.github.cawboyroy.expertcoursestudy.views.check.UpdateCheckButton
import java.io.Serializable

interface CheckUiState : Serializable {

    fun update(updateCheckButton: UpdateCheckButton)

    abstract class Abstract(
        private val visible: Int,
        private val enabled: Boolean
    ) : CheckUiState {

        override fun update(updateCheckButton: UpdateCheckButton) {
            updateCheckButton.update(visible, enabled)
        }
    }

    object Disabled : Abstract(View.VISIBLE, false)

    object Enabled : Abstract(View.VISIBLE, true)

    object Invisible : Abstract(View.GONE, false)
}