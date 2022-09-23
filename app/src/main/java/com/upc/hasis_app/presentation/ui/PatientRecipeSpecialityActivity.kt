package com.upc.hasis_app.presentation.ui

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.upc.hasis_app.databinding.ActivityPatientRecipeSpecialityBinding
import com.upc.hasis_app.databinding.ActivityPrescriptionBinding
import com.upc.hasis_app.domain.entity.Speciality
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.presentation.view_model.PatientDetailViewModel
import com.upc.hasis_app.presentation.view_model.RecipeViewModel
import com.upc.hasis_app.presentation.view_model.RegisterRecipeViewModel
import com.upc.hasis_app.presentation.view_model.ResultStatus
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PatientRecipeSpecialityActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityPatientRecipeSpecialityBinding
    lateinit var ttsHelper : TTSHelper


    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ttsHelper = TTSHelper(this, this)

        binding = ActivityPatientRecipeSpecialityBinding.inflate(layoutInflater)

        setContentView(binding.root)



        val a = PatientRecipeSpecialityActivityArgs.fromBundle(intent.extras!!).specialityId
        preferencesUseCase.setSpecialitySelected(Speciality(a,"","-"))
        println(a)

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = ttsHelper.tts!!.setLanguage(Locale("es", "ES"))

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {  }
        }
    }

}