package com.github.cawboyroy.expertcoursestudy.views.progress

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ProgressBar
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.UpdateVisibility
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.VisibilitySavedState
import com.github.cawboyroy.expertcoursestudy.views.visiblebutton.VisibilityUiState

class LoadView  : ProgressBar, UpdateVisibility {

    private lateinit var state: VisibilityUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )    
        override fun onSaveInstanceState(): Parcelable? {
            return super.onSaveInstanceState()?.let {
                val savedState = VisibilitySavedState (it)
                savedState.save(state)
                return savedState
            }
        }
    
        override fun onRestoreInstanceState(state: Parcelable?) {
            val restoredState = state as VisibilitySavedState
            super.onRestoreInstanceState(restoredState.superState)
            update(restoredState.restore())
        }

    override fun update(state: VisibilityUiState) {
        this.state = state
        state.update(this)
    }

    override fun update(visibility: Int) {
        this.visibility = visibility
    }
}