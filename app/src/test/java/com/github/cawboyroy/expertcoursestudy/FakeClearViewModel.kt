package com.github.cawboyroy.expertcoursestudy

import com.github.cawboyroy.expertcoursestudy.di.ClearViewModel
import com.github.cawboyroy.expertcoursestudy.di.MyViewModel

class FakeClearViewModel : ClearViewModel {

    var clasz: Class<out MyViewModel> = FakeViewModel::class.java

    override fun clear(viewModelClass: Class<out MyViewModel>) {
        clasz = viewModelClass
    }
}

private class FakeViewModel : MyViewModel