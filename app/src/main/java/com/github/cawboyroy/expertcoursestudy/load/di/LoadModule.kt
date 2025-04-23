package com.github.cawboyroy.expertcoursestudy.load.di

import android.R.attr.level
import com.github.cawboyroy.expertcoursestudy.core.Core
import com.github.cawboyroy.expertcoursestudy.game.di.AbstractProvideViewModel
import com.github.cawboyroy.expertcoursestudy.game.di.Module
import com.github.cawboyroy.expertcoursestudy.game.di.ProvideViewModel
import com.github.cawboyroy.expertcoursestudy.load.HandleLoading
import com.github.cawboyroy.expertcoursestudy.load.LoadViewModel
import com.github.cawboyroy.expertcoursestudy.load.data.ForegroundWrapper
import com.github.cawboyroy.expertcoursestudy.load.data.LoadRepository
import com.github.cawboyroy.expertcoursestudy.load.data.cache.WordsCacheDataSource
import com.github.cawboyroy.expertcoursestudy.load.data.cloud.HandleError
import com.github.cawboyroy.expertcoursestudy.load.data.cloud.WordsCloudDataSource
import com.github.cawboyroy.expertcoursestudy.load.data.cloud.WordsService
import com.github.cawboyroy.expertcoursestudy.load.presentation.LoadUiObservable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoadModule(
    private val core: Core
) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        return LoadViewModel(
            HandleLoading.Base(
                core.clearViewModel,
                HandleError.DomainToUi()
            ),
            if (core.runUiTests)
                LoadRepository.Fake(core.indexCache)
            else
                LoadRepository.Base(
                    ForegroundWrapper.Base(core.context),
                    WordsCloudDataSource.Base(
                        core.wordsSize,
                        Retrofit.Builder()
                            .baseUrl("https://ao0ixd.buildship.run")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(
                                OkHttpClient.Builder()
                                    .addInterceptor(HttpLoggingInterceptor() {
                                        level = HttpLoggingInterceptor.Level.BODY
                                    })
                                    .build()
                            )
                            .build()
                            .create(WordsService::class.java)
                    ),
                    WordsCacheDataSource.Base(
                        core.dao()
                    ),
                    core.indexCache,
                    HandleError.DataToDomain()
                ),
            LoadUiObservable.Base(),
            core.runAsync,
        )
    }
}

class ProvideLoadViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, LoadViewModel::class.java) {

    override fun module() = LoadModule(core)
}