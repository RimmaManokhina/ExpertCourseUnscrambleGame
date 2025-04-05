package com.github.cawboyroy.expertcoursestudy.load

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.cawboyroy.expertcoursestudy.databinding.FragmentLoadBinding
import com.github.cawboyroy.expertcoursestudy.di.ProvideViewModel
import com.github.cawboyroy.expertcoursestudy.game.GameUiState
import com.github.cawboyroy.expertcoursestudy.game.NavigateToGame

class LoadFragment: Fragment() {

    private var _binding: FragmentLoadBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var uiState: GameUiState
    private lateinit var viewModel: LoadViewModel

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            uiState = viewModel.handleUserInput(text = s.toString())
            update.invoke()
        }
    }

    private val update: (LoadUiState) -> Unit = { uiState ->
        uiState.show(
            binding.errorTextView,
            binding.retryButton,
            binding.progressBar
        )
        uiState.navigate((requireActivity() as NavigateToGame))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).makeViewModel(LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

            viewModel.load(isFirstRun = savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates(observer = update)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

