package com.github.cawboyroy.expertcoursestudy.load.presentation

import com.github.cawboyroy.expertcoursestudy.load.UiObservable

interface LoadUiObservable: UiObservable<LoadUiState> {

    class Base : UiObservable.Abstract<LoadUiState>(), LoadUiObservable
}