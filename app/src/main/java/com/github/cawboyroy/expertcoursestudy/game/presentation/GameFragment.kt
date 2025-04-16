package com.github.cawboyroy.expertcoursestudy.game.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.cawboyroy.expertcoursestudy.databinding.FragmentGameBinding
import com.github.cawboyroy.expertcoursestudy.game.di.ProvideViewModel
import com.github.cawboyroy.expertcoursestudy.stats.NavigateToStats

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var uiState: GameUiState
    private lateinit var viewModel: GameViewModel

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            uiState = viewModel.handleUserInput(text = s.toString())
            update.invoke()
        }
    }

    val update: () -> Unit = {
        uiState.update(
            binding.shuffledWordTextView,
            binding.inputView,
            binding.checkButton,
            binding.skipButton,
            binding.nextButton
        )
        uiState.navigate(requireActivity() as NavigateToStats)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: GameViewModel =
            (requireActivity() as ProvideViewModel).makeViewModel(GameViewModel::class.java)

        lateinit var uiState: GameUiState

        binding.checkButton.setOnClickListener {
            uiState = viewModel.check(text = binding.inputView.text())
            update.invoke()
        }

        binding.skipButton.setOnClickListener {
            uiState = viewModel.skip()
            update.invoke()
        }

        binding.nextButton.setOnClickListener {
            uiState = viewModel.next()
            update.invoke()
        }

        uiState = viewModel.init(savedInstanceState == null)

        update.invoke()
    }

    override fun onResume() {
        super.onResume()
        binding.inputView.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.inputView.removeTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}