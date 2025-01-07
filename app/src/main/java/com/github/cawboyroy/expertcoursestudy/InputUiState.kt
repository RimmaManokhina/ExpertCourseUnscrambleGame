package com.github.cawboyroy.expertcoursestudy

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface InputUiState {

    fun update(inputLayout: TextInputLayout, inputEditText: TextInputEditText)

    abstract class Abstract(
        private val errorIsVisible: Boolean,
        private val enabled: Boolean,
        private val clearTextInput: Boolean
    ) : InputUiState {

        override fun update(inputLayout: TextInputLayout, inputEditText: TextInputEditText) {
            inputLayout.isErrorEnabled = errorIsVisible
            if (errorIsVisible)
                inputLayout.error = inputLayout.context.getString(R.string.incorrect_message)
            inputLayout.isEnabled = enabled
            if (clearTextInput)
                inputEditText.setText(R.string.empty)
        }
    }
        object Initial : Abstract(false, true, true)

        object Sufficient : Abstract(false, true, false)
        object InSufficient : Abstract(false, true, false)

        object Correct : Abstract(false, false, false)

        object Incorrect : Abstract(true, true, false)

}