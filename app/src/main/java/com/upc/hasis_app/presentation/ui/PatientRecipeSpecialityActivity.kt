package com.upc.hasis_app.presentation.ui

import android.os.Bundle
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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PatientRecipeSpecialityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientRecipeSpecialityBinding

    val args: PatientRecipeSpecialityActivityArgs by navArgs()

    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientRecipeSpecialityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val a = PatientRecipeSpecialityActivityArgs.fromBundle(intent.extras!!).specialityId
        preferencesUseCase.setSpecialitySelected(Speciality(a,"","-"))
        println(a)

    }

}