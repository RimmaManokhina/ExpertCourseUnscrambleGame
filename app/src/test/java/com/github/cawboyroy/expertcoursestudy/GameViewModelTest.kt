package com.github.cawboyroy.expertcoursestudy

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        viewModel = GameViewModel(repository = FakeRepository())
    }

    /**
     * Unscramble game unit test
     * UGTC-01 happy pass
     * Actual = viewModel,  expected= name of the State without "state" , what data should be used
     */
    @Test
    fun caseNumber1() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial(shuffledWord = "f1")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "1")
        expected = GameUiState.NotEqualWordsLengths(shuffledWord = "f1")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "1f")
        expected = GameUiState.Sufficient(shuffledWord = "f1")
        assertEquals(expected, actual)

        actual = viewModel.check(text = "1f")
        expected = GameUiState.Correct(shuffledWord = "f1")
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.Initial(shuffledWord = "f2")
        assertEquals(expected, actual)
    }

    /**
     * UGTC-02 other unit test
     */
    @Test
    fun caseNumber2() {
        /*0 open app
        state is 1 InitialState (some word)*/
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial(shuffledWord = "f1")
        assertEquals(expected, actual)

        /*1 click Skip
        state is 1 InitialState (another word)*/
        actual = viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "f2")
        assertEquals(expected, actual)

        /*2.3 input letter
        state is 2. InSufficientState
        click Skip
        state is 1 InitialState (another word)*/
        actual = viewModel.handleUserInput(text = "f")
        expected = GameUiState.NotEqualWordsLengths(shuffledWord = "f2")
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "f3")
        assertEquals(expected, actual)

        /*4 input letter
        state is 2. InSufficientState
        5 input letter
        state is 3.SufficientState
        6 click Skip
        state is 1 InitialState (another word)*/
        actual = viewModel.handleUserInput(text = "3")
        expected = GameUiState.NotEqualWordsLengths(shuffledWord = "f3")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "3f")
        expected = GameUiState.Sufficient(shuffledWord = "f3")
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "f4")
        assertEquals(expected, actual)

        /*7 input letter
        state is 2. InSufficientState
        8 input letter
        state is 3.SufficientState
        9 click Check
        state is 4 InCorrectState
        10 click Skip
        state is 1 InitialState (another word)*/
        actual = viewModel.handleUserInput(text = "r")
        expected = GameUiState.NotEqualWordsLengths(shuffledWord = "f4")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "r4")
        expected = GameUiState.Sufficient(shuffledWord = "f4")
        assertEquals(expected, actual)

        actual = viewModel.check(text = "4g")
        expected = GameUiState.Incorrect(shuffledWord = "f4")
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "f5")
        assertEquals(expected, actual)

        /*11 input letter
        state is 2. NotEqualWordsLengths
        12 input letter
        state is 3.SufficientState
        13 click Check
        state is 4 InCorrectState
        14 remove 1 letter
        state is (2. NotEqualWordsLengths)
        15 input letters
        state is 3.SufficientState
        16 remove letters
        state is NotEqualWordsLengths
        17 input more letters
        state is NotEqualWordsLengths
        18 input more letters
        state is NotEqualWordsLengths*/
        actual = viewModel.handleUserInput(text = "f")
        expected = GameUiState.NotEqualWordsLengths(shuffledWord = "f5")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "6f")
        expected = GameUiState.Sufficient(shuffledWord = "f5")
        assertEquals(expected, actual)

        actual = viewModel.check(text = "6f")
        expected = GameUiState.Incorrect(shuffledWord = "f5")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "6")
        expected = GameUiState.NotEqualWordsLengths(shuffledWord = "f5")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "5f")
        expected = GameUiState.Sufficient(shuffledWord = "f5")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "5")
        expected = GameUiState.NotEqualWordsLengths(shuffledWord = "f5")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "5ff")
        expected = GameUiState.NotEqualWordsLengths(shuffledWord = "f5")
        assertEquals(expected, actual)
    }
}

/** GameRepository - интерфейс
 * class FakeRepository имплементирует интерфейс, поэтому Override fun
 */
private class FakeRepository : GameRepository {

    private val originalList = listOf("1f", "2f", "3f", "4f","5f")

    private val shuffledList = originalList.map {it.reversed()}

    private var index = 0

    override fun shuffledWord(): String = shuffledList[index]

    override fun originalWord(): String = originalList[index]

    override fun next() {
        index++
        if (index == originalList.size)
            index = 0
    }
}