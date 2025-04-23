package com.github.cawboyroy.expertcoursestudy.game.presentation

import com.github.cawboyroy.expertcoursestudy.game.di.ClearViewModel
import com.github.cawboyroy.expertcoursestudy.game.di.MyViewModel
import com.github.cawboyroy.expertcoursestudy.game.data.GameRepository
import com.github.cawboyroy.expertcoursestudy.load.RunAsync

class GameViewModel(
    runAsync: RunAsync,
    observable: GameObservable,
    private val repository: GameRepository,
    private val clearViewModel: ClearViewModel
) : MyViewModel.Abstract<GameUiState>(runAsync, observable) {

    private val updateUi: (GameUiState) -> Unit = {
        observable.postUiState(it)
    }

    fun next() {
        repository.next()
        return init()
    }

    fun check(text: String) {
        runAsync({
            if (repository.isCorrect(text))
                GameUiState.Correct
            else
                GameUiState.Incorrect
        }, updateUi)

    }

    fun skip() {
        repository.skip()
        return init()
    }

    fun handleUserInput(text: String) {
        runAsync({
            repository.saveUserInput(text)
            val shuffledWord = repository.shuffledWord()
            val isSufficient = text.length == shuffledWord.length
            if (isSufficient)
                GameUiState.Sufficient
            else
                GameUiState.Insufficient
        }, updateUi)
    }

    fun init(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            runAsync({
                if (repository.isLastWord()) {
                    clearViewModel.clear(GameViewModel::class.java)
                    GameUiState.GameOver
                } else {
                    val shuffledWord = repository.shuffledWord()
                    GameUiState.Initial(shuffledWord, repository.userInput())
                }
            }, updateUi)
        }
    }
}