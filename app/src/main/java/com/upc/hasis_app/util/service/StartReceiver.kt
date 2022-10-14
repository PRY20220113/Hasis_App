package com.upc.hasis_app.util.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StartReceiver: BroadcastReceiver() {


    @Inject
    lateinit var preferencesUseCase : PreferencesUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action == Intent.ACTION_BOOT_COMPLETED && preferencesUseCase.getServiceStatus() == ServiceState.STARTED.name) {
            Intent(context, NotificationService::class.java).also {
                it.action = Actions.START.name
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.i("StartReceiver","Starting the service in >=26 Mode from a BroadcastReceiver")
                    context!!.startForegroundService(it)
                    return
                }
                Log.i("StartReceiver","Starting the service in < 26 Mode from a BroadcastReceiver")
                context!!.startService(it)
            }
        }
    }
}