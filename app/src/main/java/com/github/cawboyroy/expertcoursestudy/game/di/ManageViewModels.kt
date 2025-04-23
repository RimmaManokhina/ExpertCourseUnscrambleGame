package com.github.cawboyroy.expertcoursestudy.game.di

import androidx.collection.mutableScatterMapOf

interface ManageViewModels : ProvideViewModel, ClearViewModel {

    class Factory(
        private val make: ProvideViewModel
    ) : ManageViewModels {

        private val viewModelsMap = mutableScatterMapOf<Class<out MyViewModel>, MyViewModel?>()

        override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T =
            if (viewModelsMap[clasz] == null) {
                val viewModel = make.makeViewModel(clasz)
                viewModelsMap[clasz] = viewModel
                viewModel
            } else
                viewModelsMap[clasz] as T

        override fun clear(viewModelClass: Class<out MyViewModel>) {
            viewModelsMap[viewModelClass] = null
        }
    }
}