package com.upc.hasis_app

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.response.ObtenerFactosResponse
import com.upc.hasis_app.databinding.ActivityMainBinding
import com.upc.hasis_app.domain.usecase.ObtenerFactosUseCase
import com.upc.hasis_app.presentation.view_model.WelcomeStatus
import com.upc.hasis_app.presentation.view_model.WelcomeViewModel
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.multibindings.IntKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {



    private lateinit var binding: ActivityMainBinding
    private val viewModel: WelcomeViewModel by viewModels()
    lateinit var ttsHelper : TTSHelper

    @Inject
    lateinit var obtenerFactosUseCase: ObtenerFactosUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        ttsHelper = TTSHelper(this, this)

        setContentView(view)
        GlobalScope.launch(Dispatchers.Main) {
            Log.i("test", obtenerFactosUseCase.obtenerFactosUseCase().toString());
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