package com.github.cawboyroy.expertcoursestudy

import com.github.cawboyroy.expertcoursestudy.game.data.GameRepository
import com.github.cawboyroy.expertcoursestudy.game.GameUiState
import com.github.cawboyroy.expertcoursestudy.game.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private lateinit var clearViewModel: FakeClearViewModel

    @Before
    fun setup() {
        clearViewModel = FakeClearViewModel()
        viewModel = GameViewModel(
            repository = FakeRepository(),
            clearViewModel = clearViewModel)
    }

    /**Unscramble game unit test
     * UGTC-01 happy pass
     * Actual = viewModel,  expected= name of the State without "state" , what data should be used*/
    @Test
    fun caseNumber1() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial(shuffledWord = "f1")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "1")
        expected = GameUiState.Insufficient
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "1f")
        expected = GameUiState.Sufficient
        assertEquals(expected, actual)

        actual = viewModel.check(text = "1f")
        expected = GameUiState.Correct
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
        actual = viewModel.handleUserInput(text = "2")
        expected = GameUiState.Insufficient
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
        expected = GameUiState.Insufficient
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "3f")
        expected = GameUiState.Sufficient
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
        expected = GameUiState.Insufficient
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "r4")
        expected = GameUiState.Sufficient
        assertEquals(expected, actual)

        actual = viewModel.check(text = "4g")
        expected = GameUiState.Incorrect
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "f5")
        assertEquals(expected, actual)

        /*11 input letter
        state is 2. Insufficient
        12 input letter
        state is 3.SufficientState
        13 click Check
        state is 4 InCorrectState
        14 remove 1 letter
        state is (2. Insufficient)
        15 input letters
        state is 3.SufficientState
        16 remove letters
        state is Insufficient
        17 input more letters
        state is Insufficient
        18 input more letters
        state is Insufficient*/
        actual = viewModel.handleUserInput(text = "f")
        expected = GameUiState.Insufficient
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "6f")
        expected = GameUiState.Sufficient
        assertEquals(expected, actual)

        actual = viewModel.check(text = "6f")
        expected = GameUiState.Incorrect
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "6")
        expected = GameUiState.Insufficient
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "5f")
        expected = GameUiState.Sufficient
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "5")
        expected = GameUiState.Insufficient
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "5ff")
        expected = GameUiState.Insufficient
        assertEquals(expected, actual)
    }

    @Test
    fun testLastWordNext() {
        viewModel = GameViewModel(
            repository = FakeRepository(listOf("one", "two")),
            clearViewModel = clearViewModel        )

        var actual: GameUiState = viewModel.init(isFirstRun = true)
        var expected: GameUiState = GameUiState.Initial(shuffledWord = "one".reversed())
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "two".reversed())
        assertEquals(expected, actual)

        actual = viewModel.skip()
        expected = GameUiState.GameOver
        assertEquals(expected, actual)

        assertEquals(GameViewModel::class.java, clearViewModel.clasz)
    }

    @Test
    fun testLastWordSkip() {
        viewModel = GameViewModel(
            repository = FakeRepository(listOf("one", "two")),
            clearViewModel = clearViewModel
        )

        var actual: GameUiState = viewModel.init(isFirstRun = true)
        var expected: GameUiState = GameUiState.Initial(shuffledWord = "one".reversed())
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "one")
        expected = GameUiState.Sufficient
        assertEquals(expected, actual)

        actual = viewModel.check(text = "one")
        expected = GameUiState.Correct
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.Initial(shuffledWord = "two".reversed())
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput(text = "two")
        expected = GameUiState.Sufficient
        assertEquals(expected, actual)

        actual = viewModel.check(text = "two")
        expected = GameUiState.Correct
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.GameOver
        assertEquals(expected, actual)

        assertEquals(GameViewModel::class.java, clearViewModel.clasz)
    }
}


/** GameRepository - интерфейс
 * class FakeRepository имплементирует интерфейс, поэтому Override fun
 */
private class FakeRepository(

    private val originalList: List<String> = listOf("1f", "2f", "3f", "4f","5f")
) : GameRepository {

    private val shuffledList = originalList.map {it.reversed()}

    private var index = 0

    override fun shuffledWord(): String = shuffledList[index]

    override fun isCorrect(text: String): Boolean {
        return originalList[index].equals(text, ignoreCase = true)
    }

    override fun next() {
        index++
        saveUserInput("")
    }

    override fun skip() {
        next()
    }

    override fun isLastWord() : Boolean {
        return index == originalList.size
    }

    private var input: String = ""

    override fun saveUserInput(value: String) {
        input = value
    }

    override fun userInput(): String {
        return input
    }
}