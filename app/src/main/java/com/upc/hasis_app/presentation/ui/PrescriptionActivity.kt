package com.upc.hasis_app.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ActivityPrescriptionBinding
import com.upc.hasis_app.presentation.view_model.RegisterPrescriptionViewModel

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