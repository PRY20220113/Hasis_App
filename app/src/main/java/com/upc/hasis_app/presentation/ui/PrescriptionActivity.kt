package com.upc.hasis_app.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.upc.hasis_app.databinding.ActivityPrescriptionBinding
import com.upc.hasis_app.presentation.view_model.RegisterPrescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrescriptionBinding
    private val viewModel : RegisterPrescriptionViewModel by viewModels()

    val args: PrescriptionActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrescriptionBinding.inflate(layoutInflater)

        viewModel.setPatientId(args.patientId)

        setContentView(binding.root)

    }

}