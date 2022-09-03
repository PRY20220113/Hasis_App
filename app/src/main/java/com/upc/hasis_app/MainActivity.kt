package com.upc.hasis_app

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.databinding.ActivityMainBinding
import com.upc.hasis_app.domain.usecase.DoctorUseCase
import com.upc.hasis_app.presentation.view_model.WelcomeStatus
import com.upc.hasis_app.presentation.view_model.WelcomeViewModel
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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


        var request =  CrearDoctorRequest("Bryan", "Miramira", "bbqms@hotmail.com","71811376", "123456789", "950040337", "bryanxd1234")

        GlobalScope.launch(Dispatchers.Main) {
            Log.i("test", doctorUseCase.createDoctor(request).body().toString());
            Log.i("test", doctorUseCase.getAllDoctors().body().toString());
            Log.i("test", doctorUseCase.getDoctorById(2).body().toString());
            Log.i("test", doctorUseCase.updateDoctor(2, request).body().toString());
        }

    }

    override fun onInit(status: Int) {
       if (status == TextToSpeech.SUCCESS) {
           val result = ttsHelper.tts!!.setLanguage(Locale("es", "ES"))

           if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {  }

           viewModel.setState(WelcomeStatus.Success)
       }
    }

}