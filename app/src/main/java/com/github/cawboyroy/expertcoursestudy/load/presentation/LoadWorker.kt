package com.github.cawboyroy.expertcoursestudy.load.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.github.cawboyroy.expertcoursestudy.R
import com.github.cawboyroy.expertcoursestudy.game.di.ProvideViewModel
import com.github.cawboyroy.expertcoursestudy.load.LoadViewModel

class LoadWorker (
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val NOTIFICATION_ID = 123456987
        private const val CHANNEL_ID = "UNSCRAMBLE GAME CHANNEL ID"
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val viewModel =
        (applicationContext as ProvideViewModel).makeViewModel(LoadViewModel::class.java)

    override suspend fun doWork(): Result {
        setForeground(createForegroundInfo())
        viewModel.loadInternal()
        return Result.success()
    }

    private fun createForegroundInfo(
        title: String = applicationContext.getString(R.string.app_name),
        text: String = "LOADING"
    ): ForegroundInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            ForegroundInfo(
                NOTIFICATION_ID,
                makeNotification(title, text),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        else
            ForegroundInfo(NOTIFICATION_ID, makeNotification(title, text))
    }

    private fun makeNotification(title: String, text: String): Notification {
        val resultIntent = Intent(applicationContext, MainActivity::class.java)
        val resultPendingIntent = TaskStackBuilder.create(applicationContext)
            .addNextIntentWithParentStack(resultIntent)
            .getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentIntent(resultPendingIntent)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val name = applicationContext.getString(R.string.app_name)
        val descriptionText = "LOADING DATA"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = descriptionText
        notificationManager.createNotificationChannel(channel)
    }
}