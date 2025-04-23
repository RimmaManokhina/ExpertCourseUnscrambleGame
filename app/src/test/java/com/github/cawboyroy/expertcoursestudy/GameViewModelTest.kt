package com.github.cawboyroy.expertcoursestudy

import com.github.cawboyroy.expertcoursestudy.game.data.GameRepository
import com.github.cawboyroy.expertcoursestudy.game.presentation.GameObservable
import com.github.cawboyroy.expertcoursestudy.game.presentation.GameUiState
import com.github.cawboyroy.expertcoursestudy.game.presentation.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var observable: FakeGameObservable
    private lateinit var viewModel: GameViewModel
    private lateinit var runAsync: FakeRunAsyncImmediate
    private lateinit var repository: FakeRepository
    private lateinit var clearViewModel: FakeClearViewModel

    @Before
    fun setup() {
        clearViewModel = FakeClearViewModel()
        observable = FakeGameObservable.Base()
        runAsync = FakeRunAsyncImmediate()
        repository = FakeRepository()
        viewModel = GameViewModel(
            repository = repository,
            runAsync = runAsync,
            observable = observable,
            clearViewModel = clearViewModel
        )
    }

    /**Unscramble game unit test
     * UGTC-01 happy pass
     * Actual = viewModel,  expected= name of the State without "state" , what data should be used*/
    @Test
    fun caseNumber1() {
        viewModel.init()
        var expected: GameUiState = GameUiState.Initial(shuffledWord = "f1")
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.handleUserInput(text = "1")
        expected = GameUiState.Insufficient
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.handleUserInput(text = "1f")
        expected = GameUiState.Sufficient
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.check(text = "1f")
        expected = GameUiState.Correct
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.next()
        expected = GameUiState.Initial(shuffledWord = "f2")
        assertEquals(expected, observable.postUiStateCalledList.last())
    }

    /**
     * UGTC-02 other unit test
     */
    @Test
    fun caseNumber2() {
        /*0 open app
        state is 1 InitialState (some word)*/
        viewModel.init()
        var expected: GameUiState = GameUiState.Initial(shuffledWord = "f1")
        assertEquals(expected, observable.postUiStateCalledList.last())

        /*1 click Skip
        state is 1 InitialState (another word)*/
        viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "f2")
        assertEquals(expected, observable.postUiStateCalledList.last())

        /*2.3 input letter
        state is 2. InSufficientState
        click Skip
        state is 1 InitialState (another word)*/
        viewModel.handleUserInput(text = "1")
        expected = GameUiState.Insufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "f3")
        assertEquals(expected, observable.postUiStateCalledList.last())

        /*4 input letter
        state is 2. InSufficientState
        5 input letter
        state is 3.SufficientState
        6 click Skip
        state is 1 InitialState (another word)*/
        viewModel.handleUserInput(text = "f")
        expected = GameUiState.Insufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.handleUserInput(text = "f1")
        expected = GameUiState.Sufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "f4")
        assertEquals(expected, observable.postUiStateCalledList.last())

        /*7 input letter
        state is 2. InSufficientState
        8 input letter
        state is 3.SufficientState
        9 click Check
        state is 4 InCorrectState
        10 click Skip
        state is 1 InitialState (another word)*/
        viewModel.handleUserInput(text = "f")
        expected = GameUiState.Insufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.handleUserInput(text = "f1")
        expected = GameUiState.Sufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.check(text = "f1")
        expected = GameUiState.Incorrect
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.skip()
        expected = GameUiState.Initial(shuffledWord = "f5")
        assertEquals(expected, observable.postUiStateCalledList.last())

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
        viewModel.handleUserInput(text = "f")
        expected = GameUiState.Insufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.handleUserInput(text = "f1")
        expected = GameUiState.Sufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.check(text = "f1")
        expected = GameUiState.Incorrect
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.handleUserInput(text = "f")
        expected = GameUiState.Insufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.handleUserInput(text = "f1")
        expected = GameUiState.Sufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
        viewModel.handleUserInput(text = "f12")
        expected = GameUiState.Insufficient
        assertEquals(expected, observable.postUiStateCalledList.last())
    }

    @Test
    fun testLastWordNext() {
        repository.originalList = listOf("one", "two")

        viewModel.init(isFirstRun = true)
        var expected: GameUiState = GameUiState.Initial(shuffledWord = "one".reversed())
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.handleUserInput(text = "one")
        expected = GameUiState.Sufficient
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.check(text = "one")
        expected = GameUiState.Correct
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.next()
        expected = GameUiState.Initial(shuffledWord = "two".reversed())
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.handleUserInput(text = "two")
        expected = GameUiState.Sufficient
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.check(text = "two")
        expected = GameUiState.Correct
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.next()
        expected = GameUiState.GameOver
        assertEquals(expected, observable.postUiStateCalledList.last())

        assertEquals(GameViewModel::class.java, clearViewModel.clasz)
    }

    @Test
    fun testLastWordSkip() {
        repository.originalList = listOf("one", "two")

        viewModel.init(isFirstRun = true)

        var expected: GameUiState = GameUiState.Initial(shuffledWord = "one".reversed())
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.handleUserInput(text = "one")
        expected = GameUiState.Sufficient
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.check(text = "one")
        expected = GameUiState.Correct
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.next()
        expected = GameUiState.Initial(shuffledWord = "two".reversed())
        assertEquals(expected, observable.postUiStateCalledList.last())

        viewModel.skip()
        expected = GameUiState.GameOver
        assertEquals(expected, observable.postUiStateCalledList.last())

        assertEquals(GameViewModel::class.java, clearViewModel.clasz)
    }
}


/** GameRepository - интерфейс
 * class FakeRepository имплементирует интерфейс, поэтому Override fun
 */
private class FakeRepository : GameRepository {

    var originalList: List<String> = listOf(
        "1f", "2f", "3f", "4f", "5f", "6f"
    )

    private val shuffledList
        get() = originalList.map { it.reversed() }

    private var index = 0

    override suspend fun shuffledWord(): String = shuffledList[index]

    override suspend fun isCorrect(text: String): Boolean {
        return originalList[index].equals(text, ignoreCase = true)
    }

    override fun skip() {
        next()
    }

    override fun next() {
        index++
        saveUserInput("")
    }

    override fun isLastWord(): Boolean {
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


private interface FakeGameObservable : FakeUiObservable<GameUiState>, GameObservable {
    class Base : FakeUiObservable.Abstract<GameUiState>(), FakeGameObservable
}