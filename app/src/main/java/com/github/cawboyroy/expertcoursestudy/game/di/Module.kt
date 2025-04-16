package com.github.cawboyroy.expertcoursestudy.game.di

interface Module<T : MyViewModel> {

    fun viewModel(): T
}