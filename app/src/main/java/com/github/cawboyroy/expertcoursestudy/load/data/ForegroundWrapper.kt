package com.github.cawboyroy.expertcoursestudy.load.data

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.cawboyroy.expertcoursestudy.load.presentation.LoadWorker

interface ForegroundWrapper {

    fun start()

    class Base(context: Context) : ForegroundWrapper {

        private val workManager = WorkManager.getInstance(context)

        override fun start() {
            val request = OneTimeWorkRequestBuilder<LoadWorker>().build()
            workManager.enqueueUniqueWork(
                this::class.java.simpleName,
                ExistingWorkPolicy.KEEP,
                request
            )
        }
    }
}