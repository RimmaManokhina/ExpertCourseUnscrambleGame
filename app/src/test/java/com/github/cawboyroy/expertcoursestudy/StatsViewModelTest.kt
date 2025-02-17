package com.github.cawboyroy.expertcoursestudy

import com.github.cawboyroy.expertcoursestudy.stats.StatsUiState
import org.junit.Assert.assertEquals
import org.junit.Test

class StatsViewModelTest {

    @Test
    fun test() {
        val repository = FakeStatsRepository()
        val viewModel: StatsViewModel = StatsViewModel(repository = repository)

        var actualUiState: StatsUiState = viewModel.init(isFirstRun = true)
        assertEquals(StatsUiState.Base(1, 2, 3), actualUiState)

        actualUiState = StatsUiState.Empty

        assertEquals(StatsUiState.Empty, actualUiState)

        viewModel.clear()
        repository.assertClearCalled()
    }
}

private class FakeStatsRepository : StatsRepository {

    private var clearCalled = false

    override fun stats(): Triple<Int, Int, Int> {
        return Triple(1, 2, 3)
    }

    override fun clear() {
        clearCalled = true
    }

    fun assertClearCalled() {
        assertEquals(true, clearCalled)
    }
}
