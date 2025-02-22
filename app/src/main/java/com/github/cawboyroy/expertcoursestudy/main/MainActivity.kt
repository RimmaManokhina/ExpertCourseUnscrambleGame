package com.github.cawboyroy.expertcoursestudy.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.cawboyroy.expertcoursestudy.R
import com.github.cawboyroy.expertcoursestudy.di.MyViewModel
import com.github.cawboyroy.expertcoursestudy.di.ProvideViewModel

class MainActivity : AppCompatActivity(), Navigation, ProvideViewModel {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null)
            navigateToGame()
    }

    override fun navigate(screen: Screen) =
        screen.show(R.id.container, supportFragmentManager)

    override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T =
        (application as ProvideViewModel).makeViewModel(clasz)
}