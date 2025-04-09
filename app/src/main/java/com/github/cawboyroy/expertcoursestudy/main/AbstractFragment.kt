package com.github.cawboyroy.expertcoursestudy.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.github.cawboyroy.expertcoursestudy.core.MyViewModel

interface AbstractFragment {

    abstract class Ui<B : ViewBinding> : Fragment() {

        private var _binding: B? = null

        protected val binding
            get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = inflate(inflater, container)
            return binding.root
        }

        protected abstract fun inflate(
            inflater: LayoutInflater,
            container: ViewGroup?
        ): B

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }

    abstract class Async<UiState : Any, T : MyViewModel.Async<UiState>, B : ViewBinding> : Ui<B>() {

        protected lateinit var viewModel: T

        protected abstract val update: (UiState) -> Unit

        override fun onResume() {
            super.onResume()
            viewModel.startUpdates(observer = update)
        }

        override fun onPause() {
            super.onPause()
            viewModel.stopUpdates()
        }
    }
}