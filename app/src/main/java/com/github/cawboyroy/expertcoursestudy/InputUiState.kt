package com.github.cawboyroy.expertcoursestudy

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.Serializable

interface InputUiState : Serializable {

    fun update(inputLayout: TextInputLayout, inputEditText: TextInputEditText)

    abstract class Abstract(
        private val errorIsVisible: Boolean,
        private val enabled: Boolean
    ) : InputUiState {

        override fun update(inputLayout: TextInputLayout, inputEditText: TextInputEditText) {
            inputLayout.isErrorEnabled = errorIsVisible
            if (errorIsVisible)
                inputLayout.error = inputLayout.context.getString(R.string.incorrect_message)
            inputLayout.isEnabled = enabled
        }
    }

    data class Initial(
        private val userInput: String
    ) : Abstract(false, true) {
        override fun update(inputLayout: TextInputLayout, inputEditText: TextInputEditText) {
            super.update(inputLayout, inputEditText)
            inputEditText.setText(userInput)
        }
    }
        object Sufficient : Abstract(false, true)
        object Insufficient : Abstract(false, true)
        object Correct : Abstract(false, false)
        object Incorrect : Abstract(true, true)
}
