package com.github.cawboyroy.expertcoursestudy.stats

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.github.cawboyroy.expertcoursestudy.R

class StatsTextView : AppCompatTextView, UpdateStats {

    private lateinit var state: StatsUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
        override fun onSaveInstanceState(): Parcelable? {
            return super.onSaveInstanceState()?.let {
                val savedState = StatsSavedState (it)
                savedState.save(state)
                return savedState
            }
        }

        override fun onRestoreInstanceState(state: Parcelable?) {
            val restoredState = state as StatsSavedState
            super.onRestoreInstanceState(restoredState.superState)
            update(restoredState.restore())
        }

    override fun update(outer: StatsUiState) {
        state = outer
        state.show(this)
    }

    override fun update(skips: Int, fails: Int, corrects: Int) {
        setText(resources.getString(R.string.stats_info, skips, fails, corrects))
        }
    }

    interface UpdateStats {

        fun update(outer: StatsUiState)

        fun update(skips: Int, fails: Int, corrects: Int)
    }
