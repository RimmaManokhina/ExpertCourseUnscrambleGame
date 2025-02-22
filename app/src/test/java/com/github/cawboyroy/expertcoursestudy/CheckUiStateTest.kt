package com.github.cawboyroy.expertcoursestudy

import android.view.View
import com.github.cawboyroy.expertcoursestudy.game.CheckUiState
import com.github.cawboyroy.expertcoursestudy.views.check.UpdateCheckButton
import junit.framework.TestCase.assertEquals
import org.junit.Test


class CheckUiStateTest {

    @Test
    fun testEnabled() {
        val enabled = CheckUiState.Enabled
        val button = FakeButton()

        enabled.update(button)

        assertEquals(View.VISIBLE, button.visibility)
        assertEquals(true, button.enabled)
    }

    @Test
    fun testDisabled() {
        val enabled = CheckUiState.Disabled
        val button = FakeButton()

        enabled.update(button)

        assertEquals(View.VISIBLE, button.visibility)
        assertEquals(false, button.enabled)
    }

    @Test
    fun testInvisible() {
        val checkUiState = CheckUiState.Invisible
        val button = FakeButton()

        checkUiState.update(button)

        assertEquals(View.GONE, button.visibility)
        assertEquals(false, button.enabled)
    }
}

private class FakeButton : UpdateCheckButton {
    override fun update(uiState: CheckUiState) {
        throw IllegalStateException("not used here")
    }

    var visibility: Int? = null
    var enabled: Boolean? = null

    override fun update(visibility: Int, enabled: Boolean) {
        this.visibility = visibility
        this.enabled = enabled
    }
}