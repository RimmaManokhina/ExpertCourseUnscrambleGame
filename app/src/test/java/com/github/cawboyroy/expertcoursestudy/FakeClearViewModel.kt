package com.github.cawboyroy.expertcoursestudy

import com.github.cawboyroy.expertcoursestudy.core.MyViewModel
import com.github.cawboyroy.expertcoursestudy.di.ClearViewModel
import junit.framework.TestCase.assertEquals


class FakeClearViewModel : ClearViewModel {

    var clasz: Class<out MyViewModel> = FakeViewModel::class.java

    override fun clear(viewModelClass: Class<out MyViewModel>) {
        clasz = viewModelClass
    }

    fun assertClearCalled(expected: Class<out MyViewModel>) {
        assertEquals(expected, clasz)
    }
}

private class FakeViewModel : MyViewModel