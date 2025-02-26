package com.github.cawboyroy.expertcoursestudy

import com.github.cawboyroy.expertcoursestudy.stats.data.StatsRepository
import com.github.cawboyroy.expertcoursestudy.stats.StatsUiState
import com.github.cawboyroy.expertcoursestudy.stats.StatsViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class StatsViewModelTest {

    @Test
    fun test() {
        val repository = FakeStatsRepository()
        val clearViewModel = FakeClearViewModel()
        val viewModel: StatsViewModel = StatsViewModel(
            repository = repository,
            clearViewModel = clearViewModel
        )

        var actualUiState: StatsUiState = viewModel.init(isFirstRun = true)
        assertEquals(StatsUiState.Base(1, 2, 3), actualUiState)
        repository.assertClearCalled()

        actualUiState = StatsUiState.Empty
        assertEquals(StatsUiState.Empty, actualUiState)

        viewModel.clear()
        assertEquals(StatsViewModel::class.java, clearViewModel.clasz)
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