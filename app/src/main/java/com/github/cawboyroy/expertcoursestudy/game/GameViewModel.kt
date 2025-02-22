package com.github.cawboyroy.expertcoursestudy.game

import com.github.cawboyroy.expertcoursestudy.di.ClearViewModel
import com.github.cawboyroy.expertcoursestudy.di.MyViewModel
import com.github.cawboyroy.expertcoursestudy.game.data.GameRepository

class GameViewModel(
    private val repository: GameRepository,
    private val clearViewModel: ClearViewModel
) : MyViewModel {

    fun next(): GameUiState {
        repository.next()
        return init()
    }

    fun check(text: String): GameUiState = if (repository.isCorrect(text))
            GameUiState.Correct
        else
            GameUiState.Incorrect

    fun skip(): GameUiState {
        repository.skip()
        return init()
    }

    fun handleUserInput(text: String): GameUiState {
        repository.saveUserInput(text)
        val shuffledWord = repository.shuffledWord()
        val isSufficient = text.length == shuffledWord.length
        return if (isSufficient)
            GameUiState.Sufficient
        else
            GameUiState.Insufficient
    }

    fun init(isFirstRun: Boolean = true): GameUiState {
        return if (isFirstRun) {
            if (repository.isLastWord()) {
                clearViewModel.clear(GameViewModel::class.java)
                GameUiState.GameOver
            } else {
                val shuffledWord = repository.shuffledWord()
                return GameUiState.Initial(shuffledWord, repository.userInput())
            }
        } else
            GameUiState.Empty
    }
}