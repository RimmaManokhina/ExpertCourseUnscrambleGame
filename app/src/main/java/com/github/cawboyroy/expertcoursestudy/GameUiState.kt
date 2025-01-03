package com.github.cawboyroy.expertcoursestudy

import com.github.cawboyroy.expertcoursestudy.databinding.ActivityMainBinding

interface GameUiState {

    data class Initial (val shuffledWord: String) : GameUiState

    data class NotEqualWordsLengths (val shuffledWord: String) : GameUiState

    data class Sufficient(val shuffledWord: String) : GameUiState

    data class Correct(val shuffledWord: String) : GameUiState

    data class Incorrect(val shuffledWord: String) : GameUiState

    fun update(binding: ActivityMainBinding) : Unit =
        throw IllegalStateException("Rimma, handle this")    //todo remove it
}
