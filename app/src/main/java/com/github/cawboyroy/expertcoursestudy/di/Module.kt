package com.github.cawboyroy.expertcoursestudy.di

interface Module<T : MyViewModel> {

    fun viewModel(): T
}