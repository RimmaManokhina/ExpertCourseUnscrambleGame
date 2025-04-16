package com.github.cawboyroy.expertcoursestudy.game.di

import android.app.Application

class UnscrambleApp : Application(), ProvideViewModel {

    private lateinit var factory: ManageViewModels

    override fun onCreate() {
        super.onCreate()
        val clearViewModel = object : ClearViewModel {
            override fun clear(viewModelClass: Class<out MyViewModel>) =
                factory.clear(viewModelClass)
        }
        val make = ProvideViewModel.Make(Core(this, clearViewModel))
        factory = ManageViewModels.Factory(make)
    }

    override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T =
        factory.makeViewModel(clasz)
}