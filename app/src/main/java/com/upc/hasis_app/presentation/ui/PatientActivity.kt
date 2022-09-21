package com.upc.hasis_app.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ActivityPatientBinding
import com.upc.hasis_app.presentation.ui.doctor.DoctorSpecialitiesFragment
import com.upc.hasis_app.presentation.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityPatientBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nvOptions.setupWithNavController(findNavController(R.id.fragmentPatientContainerView))

//        binding.nvOptions.setOnItemSelectedListener {
//
//            when(it.itemId) {
//                R.id.patientConsultFragment -> replaceFragment(DoctorSpecialitiesFragment())
//                R.id.profileFragment -> replaceFragment(ProfileFragment())
//            }
//            true
//        }
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentPatientContainerView, fragment)
        fragmentTransaction.commit()
    }
}