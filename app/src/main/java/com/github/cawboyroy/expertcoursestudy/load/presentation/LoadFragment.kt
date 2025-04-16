package com.github.cawboyroy.expertcoursestudy.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.cawboyroy.expertcoursestudy.databinding.FragmentLoadBinding
import com.github.cawboyroy.expertcoursestudy.game.di.ProvideViewModel
import com.github.cawboyroy.expertcoursestudy.game.presentation.NavigateToGame
import com.github.cawboyroy.expertcoursestudy.load.presentation.LoadUiState
import com.github.cawboyroy.expertcoursestudy.main.AbstractFragment

class LoadFragment : AbstractFragment.Async<LoadUiState, LoadViewModel, FragmentLoadBinding>() {

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoadBinding.inflate(inflater, container, false)

    override val update: (LoadUiState) -> Unit = { uiState ->
        uiState.show(
            binding.errorTextView,
            binding.retryButton,
            binding.progressBar
        )
        uiState.navigate((requireActivity() as NavigateToGame))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).makeViewModel(LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.load(isFirstRun = savedInstanceState == null)
    }
}