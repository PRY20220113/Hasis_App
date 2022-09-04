package com.upc.hasis_app.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ActivityDoctorBinding
import com.upc.hasis_app.presentation.ui.patient.PatientConsultFragment
import com.upc.hasis_app.presentation.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDoctorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoctorBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.nvOptions.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.patientConsultFragment -> replaceFragment(PatientConsultFragment())
                R.id.profileFragment -> replaceFragment(ProfileFragment())
            }

            true
        }

    }

    private fun replaceFragment(fragment : Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragmentDoctorContainerView, fragment)
        fragmentTransaction.commit()

    }
}