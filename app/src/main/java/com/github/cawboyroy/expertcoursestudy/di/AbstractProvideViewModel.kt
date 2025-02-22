package com.github.cawboyroy.expertcoursestudy.di

import com.github.cawboyroy.expertcoursestudy.di.Core
import com.github.cawboyroy.expertcoursestudy.di.Module
import com.github.cawboyroy.expertcoursestudy.di.ProvideViewModel

@Suppress("UNCHECKED_CAST")
abstract class AbstractProvideViewModel (
    protected val core: Core,
    private val nextChain: ProvideViewModel,
    private val viewModelClass: Class<out MyViewModel>
) : ProvideViewModel {

    override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T {
        return if (clasz == viewModelClass)
            module().viewModel() as T
        else
            nextChain.makeViewModel(clasz)
    }

    protected abstract fun module(): Module<*>
}