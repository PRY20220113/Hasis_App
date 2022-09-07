package com.upc.hasis_app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.upc.hasis_app.databinding.ActivityMainBinding
import com.upc.hasis_app.domain.usecase.DoctorUseCase
import com.upc.hasis_app.presentation.view_model.ResultStatus
import com.upc.hasis_app.presentation.view_model.WelcomeViewModel
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {


    private lateinit var binding: ActivityMainBinding
    private val viewModel: WelcomeViewModel by viewModels()
    lateinit var ttsHelper : TTSHelper

    @Inject
    lateinit var doctorUseCase: DoctorUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        ttsHelper = TTSHelper(this, this)

        setContentView(view)

        checkRecordPermissions()

    }

    override fun onInit(status: Int) {
       if (status == TextToSpeech.SUCCESS) {
           val result = ttsHelper.tts!!.setLanguage(Locale("es", "ES"))

           if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {  }

           viewModel.setState(ResultStatus.Success)
       }
    }

    private fun checkRecordPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RecordAudioRequestCode)
            }
        }
    }

    companion object{
        const val RecordAudioRequestCode = 1
    }

}