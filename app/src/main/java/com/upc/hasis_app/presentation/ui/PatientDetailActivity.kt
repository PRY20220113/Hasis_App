package com.upc.hasis_app.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.upc.hasis_app.databinding.ActivityPrescriptionBinding
import com.upc.hasis_app.presentation.view_model.PatientDetailViewModel
import com.upc.hasis_app.presentation.view_model.RecipeViewModel
import com.upc.hasis_app.presentation.view_model.RegisterRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrescriptionBinding
    private val prescriptionViewModel : RegisterRecipeViewModel by viewModels()
    private val recipeViewModel : RecipeViewModel by viewModels()
    private val patientViewModel: PatientDetailViewModel by viewModels()

    val args: PatientDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrescriptionBinding.inflate(layoutInflater)

        prescriptionViewModel.setPatientId(args.patientId)
        recipeViewModel.setPatientId(args.patientId)

        patientViewModel.getPatientDetail(args.patientId)

        setContentView(binding.root)

    }

}