package com.github.cawboyroy.expertcoursestudy.game.presentation

import com.github.cawboyroy.expertcoursestudy.load.UiObservable

interface GameObservable : UiObservable<GameUiState> {

    class Base : UiObservable.Abstract<GameUiState>(), GameObservable
}