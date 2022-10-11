package com.upc.hasis_app.util.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.upc.hasis_app.R
import com.upc.hasis_app.domain.entity.Schedule
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : Service() {

    private final val TAG = "NotificationService"

    val handler = Handler()
    lateinit var actualShedule : Schedule

    val CHANNEL_ID = "hasis_channel"

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
                    Log.d(TAG, getNowDateRounded().toString())
                    if(verifySchedule()) NotificationManagerCompat.from(applicationContext).notify(1,getNotification())
                    postDelayed(this, 60000)
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

    private fun verifySchedule(): Boolean{
        for (schedule in preferencesUseCase.getSchedules()!!)
        {
            if(getNowDateRounded() == LocalDateTime.parse(schedule.localDate)){
                actualShedule = schedule
                return true
            }
        }
        return false
    }


    private fun getNowDateRounded(): LocalDateTime {
        return LocalDate.now().atTime(LocalDateTime.now().hour, LocalDateTime.now().minute)
    }

    private fun getNotification(): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Pastillero Hasis",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
            val builder = Notification.Builder(
                applicationContext, CHANNEL_ID
            ).setAutoCancel(true)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setContentText("Hora de tomar ${actualShedule.quantity} tableta/s de ${actualShedule.weight} miligramos de tu pastilla ${actualShedule.name}")
            builder.build()
        } else {
            val builder = NotificationCompat.Builder(this)
                .setContentTitle("Pastillero Hasis")
                .setContentText("Hora de tomar ${actualShedule.quantity} tableta/s de ${actualShedule.weight} miligramos de tu pastilla ${actualShedule.name}")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
            builder.build()
        }
    }
}