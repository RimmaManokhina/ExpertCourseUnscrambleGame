package com.github.cawboyroy.expertcoursestudy.di

import com.github.cawboyroy.expertcoursestudy.core.MyViewModel

interface ClearViewModel {

    fun clear(viewModelClass: Class<out MyViewModel>)
}