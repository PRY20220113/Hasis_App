package com.upc.hasis_app.presentation.ui

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.upc.hasis_app.databinding.ActivityPatientBinding
import com.upc.hasis_app.presentation.ui.doctor.DoctorSpecialitiesFragment
import com.upc.hasis_app.presentation.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class PatientActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityPatientBinding

    private val profileFragment: Fragment = ProfileFragment()
    private val doctorSpecialitiesFragment: Fragment = DoctorSpecialitiesFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fm = supportFragmentManager
        var active = doctorSpecialitiesFragment;

        fm.beginTransaction().add(com.upc.hasis_app.R.id.fragmentPatientContainerView, profileFragment, "2").hide(profileFragment).commit()
        fm.beginTransaction().add(com.upc.hasis_app.R.id.fragmentPatientContainerView, doctorSpecialitiesFragment, "1").commit()



        binding.nvOptions.setOnItemSelectedListener {
            when(it.itemId){
                com.upc.hasis_app.R.id.doctorSpecialitiesFragment -> {
                    fm.beginTransaction().hide(active).show(doctorSpecialitiesFragment).commit();
                    active = doctorSpecialitiesFragment;
                    return@setOnItemSelectedListener true
                }
                com.upc.hasis_app.R.id.profileFragment -> {
                    fm.beginTransaction().hide(active).show(profileFragment).commit();
                    active = profileFragment;
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }


}