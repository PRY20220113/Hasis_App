package com.upc.hasis_app.util.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.upc.hasis_app.R
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : Service() {

    private final val TAG = "NotificationService"

    val handler = Handler()

    @Inject
    lateinit var preferencesUseCase : PreferencesUseCase

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        Log.i(TAG, "onCreate")
        super.onCreate()
        //startForeground(12345678, getNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "start")
        var contador = 0
        handler.apply {
            val runnable  = object : Runnable{
                override fun run() {
                    contador++
                    Log.d(TAG, contador.toString())
                    Log.d(TAG, preferencesUseCase.getSchedules().toString())
                    if(contador == 10) NotificationManagerCompat.from(applicationContext).notify(1,getNotification())
                    postDelayed(this, 1000)
                }
            }
            postDelayed(runnable, 1000)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "destroy")
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }



    private fun getNotification(): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_01",
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
            val builder = Notification.Builder(
                applicationContext, "channel_01"
            ).setAutoCancel(true)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentText("Toma tu pastilla mano")
            builder.build()
        } else {
            val builder = NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("My Channel")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
            builder.build()
        }
    }
}