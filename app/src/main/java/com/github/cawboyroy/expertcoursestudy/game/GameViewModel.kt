package com.github.cawboyroy.expertcoursestudy.game

class GameViewModel(
    private val repository: GameRepository
) {

    fun next(): GameUiState {
        repository.next()
        return init()
    }

    fun check(text: String): GameUiState {
        val originalWord = repository.originalWord()
        val isCorrect = originalWord.equals(text, ignoreCase = true)
        return if (isCorrect)
            GameUiState.Correct
        else
            GameUiState.Incorrect
    }

    fun skip(): GameUiState {
        repository.next()
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
        if (isFirstRun) {
            val shuffledWord = repository.shuffledWord()
            return GameUiState.Initial(shuffledWord, repository.userInput())
        } else
            return GameUiState.Empty
    }
}