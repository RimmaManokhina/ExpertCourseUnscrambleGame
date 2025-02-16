package com.github.cawboyroy.expertcoursestudy.views.input

import android.content.Context
import android.os.Parcelable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.github.cawboyroy.expertcoursestudy.game.InputUiState
import com.github.cawboyroy.expertcoursestudy.R
import com.github.cawboyroy.expertcoursestudy.databinding.InputBinding


class InputView  : FrameLayout, UpdateInput {

    private lateinit var state: InputUiState

    private val binding = InputBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    
     override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = InputViewSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as InputViewSavedState
        super.onRestoreInstanceState(restoredState.superState)
        updateUiState(restoredState.restore())
    }

    override fun updateUiState(uiState: InputUiState) {
        state = uiState
        state.update(this)
    }

    override fun updateUserInput(userInput: String) {
        binding.inputEditText.setText(userInput)
    }

    override fun updateErrorIsEnabledAndEnabled(errorIsEnabled: Boolean, enabled: Boolean) = with(binding) {
        inputLayout.isErrorEnabled = errorIsEnabled
        if (errorIsEnabled)
            inputLayout.error = inputLayout.context.getString(R.string.incorrect_message)
        inputLayout.isEnabled = enabled
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        binding.inputEditText.addTextChangedListener(textWatcher)
    }

    fun removeTextChangedListener(textWatcher: TextWatcher) {
        binding.inputEditText.removeTextChangedListener(textWatcher)
    }

    fun text(): String = binding.inputEditText.text.toString()

}

interface UpdateInput {
    fun updateUiState(uiState: InputUiState)

    fun updateUserInput(userInput: String)

    fun updateErrorIsEnabledAndEnabled(errorIsEnabled: Boolean, enabled: Boolean)
}