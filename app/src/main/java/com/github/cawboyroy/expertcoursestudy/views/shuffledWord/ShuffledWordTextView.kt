package com.github.cawboyroy.expertcoursestudy.views.shuffledWord

import android.content.Context
import android.util.AttributeSet

class ShuffledWordTextView : androidx.appcompat.widget.AppCompatTextView, UpdateText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr)

    override fun getFreezesText() = true

    override fun update(text: String) {
        this.text = text
    }
}

interface UpdateText {

    fun update(text: String)
}

