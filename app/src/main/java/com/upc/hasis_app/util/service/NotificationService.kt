package com.upc.hasis_app.util.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.upc.hasis_app.R
import com.upc.hasis_app.domain.entity.Schedule
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : Service() {


    private val TAG = "NotificationService"

    lateinit var actualShedule : Schedule

    private val CHANNEL_ID = "hasis_channel"

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    @Inject
    lateinit var preferencesUseCase : PreferencesUseCase

    var counter = 1

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG,"Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG,"onStartCommand executed with startId: $startId")
        if (intent != null) {
            val action = intent.action
            Log.i(TAG,"using an intent with action $action")
            when (action) {
                Actions.START.name -> startService()
                Actions.STOP.name -> stopService()
                else -> Log.i(TAG,"This should never happen. No action in the received intent")
            }
        } else {
            Log.i(TAG,"with a null intent. It has been probably restarted by the system.")
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG,"The service has been created".toUpperCase())
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            "HASIS - Pastillero",
            NotificationManager.IMPORTANCE_HIGH
        ).let {
            it.description = "Pastillero - HASIS"
            it.enableLights(true)
            it.lightColor = Color.BLUE
            it.enableVibration(true)
            it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            it
        }
        notificationManager.createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID).build()

        startForeground(counter, notification)
    }

    private fun startService() {
        if (isServiceStarted) return
        Log.i(TAG,"Starting the foreground service task")
        Toast.makeText(this, "Se le notificaran sus dosis", Toast.LENGTH_SHORT).show()
        isServiceStarted = true
        preferencesUseCase.setServiceStatus(ServiceState.STARTED.name)

        // we need this lock so our service gets not affected by Doze Mode
        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                    acquire()
                }
            }

        // we're starting a loop in a coroutine
        GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                launch(Dispatchers.IO) {
                    println(getNowDateRounded())
                    println(preferencesUseCase.getSchedules().toString())
                    if(verifySchedule()){
                        with(NotificationManagerCompat.from(applicationContext)) {
                            counter++
                            notify(counter, getNotification2())

                        }
                    }
                }
                delay(1 * 60 * 1000)
            }
            Log.i(TAG,"End of the loop for the service")
        }
    }

    private fun stopService() {
        Log.i(TAG,"Stopping the foreground service")
        Toast.makeText(this, "Service stopping", Toast.LENGTH_SHORT).show()
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            Log.i(TAG,"Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
        preferencesUseCase.setServiceStatus(ServiceState.STOPPED.name)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "The service has been destroyed".uppercase(Locale.getDefault()))
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show()
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

        val contentTitle= "Hora de la medicación"
        val contentText = "Hora de tu dosis de ${actualShedule.name} de las ${actualShedule.localDate.substring(11,16)}, debes ingerir  ${actualShedule.quantity} tableta(s) de ${actualShedule.weight} miligramos"

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null){
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Pastillero Hasis",
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableVibration(true)
                notificationManager.createNotificationChannel(channel)
            }
            val builder = Notification.Builder(
                applicationContext, CHANNEL_ID
            ).setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setStyle(Notification.BigTextStyle().bigText(contentText))

            builder.build()
        } else {
            val builder = NotificationCompat.Builder(this)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
            builder.build()
        }
    }


    private fun getNotification2(): Notification{

        val contentTitle= "Hora de la medicación"
        val contentText = "Hora de tu dosis de ${actualShedule.name} de las ${actualShedule.localDate.substring(11,16)}, debes ingerir  ${actualShedule.quantity} tableta(s) de ${actualShedule.weight} miligramos"


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Pastillero - HASIS",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Pastillero - HASIS"
                it.enableLights(true)
                it.lightColor = Color.BLUE
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            this,
            CHANNEL_ID
        ) else Notification.Builder(this)


        return builder
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setStyle(Notification.BigTextStyle().bigText(contentText))
            .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
            .build()
    }
}