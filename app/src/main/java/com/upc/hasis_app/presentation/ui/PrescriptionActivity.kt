package com.upc.hasis_app.presentation.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ActivityPrescriptionBinding

class PrescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}