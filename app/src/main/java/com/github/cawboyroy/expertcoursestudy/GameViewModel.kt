package com.github.cawboyroy.expertcoursestudy

class GameViewModel(private val repository: GameRepository) {

    fun init(): GameUiState {
        val shuffledWord = repository.shuffledWord()
        return GameUiState.Initial(shuffledWord)
    }

    fun handleUserInput(text: String): GameUiState {
        val shuffledWord = repository.shuffledWord()
        val isSufficient = text.length == shuffledWord.length

        return if (isSufficient)
            GameUiState.Sufficient(shuffledWord)
        else GameUiState.Insufficient(shuffledWord)
    }

    fun check(text: String): GameUiState {
        val shuffledWord = repository.shuffledWord()
        val originalWord = repository.originalWord()
        val isCorrect = originalWord.equals(text, ignoreCase = true)

        return if (isCorrect) GameUiState.Correct(shuffledWord)
        else GameUiState.Incorrect(shuffledWord)
    }

    fun skip(): GameUiState {
        repository.next()
        return init()
    }

    fun next(): GameUiState {
        repository.next()
        return init()
    }
}
