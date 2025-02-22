package com.github.cawboyroy.expertcoursestudy.di

import com.github.cawboyroy.expertcoursestudy.di.MyViewModel
import com.github.cawboyroy.expertcoursestudy.di.Core
import com.github.cawboyroy.expertcoursestudy.di.ProvideViewModel
import com.github.cawboyroy.expertcoursestudy.game.di.ProvideGameViewModel
import com.github.cawboyroy.expertcoursestudy.stats.di.ProvideGameOverViewModel


interface ProvideViewModel {

    fun <T: MyViewModel> makeViewModel(clasz: Class<T>): T

    class Make(core: Core) : ProvideViewModel {

        private var chain: ProvideViewModel

        init {
            chain = Error()
            chain = ProvideGameViewModel(core, chain)
            chain = ProvideGameOverViewModel(core, chain)
        }

        override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T =
            chain.makeViewModel(clasz)
    }

    class Error : ProvideViewModel {

        override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T {
            throw IllegalStateException("unknown class $clasz")
        }
    }
}