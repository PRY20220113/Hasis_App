package com.upc.hasis_app.presentation.ui.pharmacy

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.internal.LinkedTreeMap
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.ActivityDoctorBinding
import com.upc.hasis_app.databinding.ActivityNearbyPharmaciesBinding
import com.upc.hasis_app.domain.entity.Pharmacy
import com.upc.hasis_app.domain.usecase.PlacesUseCase
import com.upc.hasis_app.presentation.adapter.PharmacyAdapter
import com.upc.hasis_app.presentation.ui.patient.PatientConsultFragment
import com.upc.hasis_app.presentation.ui.profile.ProfileFragment
import com.upc.hasis_app.presentation.view_model.*
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PharmacyActivity : AppCompatActivity(), TextToSpeech.OnInitListener {


    private lateinit var binding: ActivityNearbyPharmaciesBinding
    private lateinit var pharmacyAdapter: PharmacyAdapter
    private var recyclerView : RecyclerView? = null
    lateinit var ttsHelper : TTSHelper
    private lateinit var context : Context
    private val viewModel : PharmacyViewModel by viewModels()
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    @Inject
    lateinit var  placesUseCase : PlacesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNearbyPharmaciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        recyclerView = binding.nearbyPharmaciesContainer
        ttsHelper = TTSHelper(this, this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        binding.progressIndicator.visibility = View.VISIBLE
        binding.nearbyPharmaciesContainer.visibility = View.GONE

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        getLastLocation()
        initObservers()

    }

    private fun initObservers(){
        viewModel.currentState.observe(this) {
            when (it) {
                is SpeakStatus.ReadyToSpeak -> {
                    viewModel.interactWithUser(ttsHelper)
                }
                is SpeakStatus.SpeakComplete -> {

                }
                else -> {
                }
            }
        }
    }

    private fun hideProgressBar(){
        binding.progressIndicator.visibility = View.GONE
        binding.nearbyPharmaciesContainer.visibility = View.VISIBLE
    }


    private fun getNearbyPharmacies(location:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = placesUseCase.getNerbyPharmacies(location)
            if (call.isSuccessful){
                val body = call.body() as LinkedTreeMap<*, *>
                runOnUiThread {
                    hideProgressBar()
                    pharmacyAdapter = PharmacyAdapter(getMappedPharmacies(body).toList(), context)
                    viewModel.updateMedicines(getMappedPharmacies(body).toList())
                    recyclerView!!.adapter = pharmacyAdapter

                    viewModel.setState(SpeakStatus.ReadyToSpeak)
                }
            } else {
                val errorMessage = call.errorBody()!!.string()
                Log.i("Response", errorMessage)
                runOnUiThread {
                    hideProgressBar()
                }
                this.cancel()
            }
        }
    }

    private fun getMappedPharmacies(map: LinkedTreeMap<*, *>): MutableList<Pharmacy> {
        val results = map["results"] as List<LinkedTreeMap<*, *>>
        val pharmacies = mutableListOf<Pharmacy>()
        for (pharmacy in results){
            pharmacies.add(
                Pharmacy(
                pharmacy["place_id"].toString() ,
                pharmacy["name"].toString(),
                ((pharmacy["geometry"] as LinkedTreeMap<*,*>)["location"] as LinkedTreeMap<*,*>).let {
                    val lat =  it.get("lat").toString()
                    val lng = it.get("lng").toString()
                    "$lat,$lng"
                }.toString() )
            )
            if (pharmacies.size >= 3) break
        }
        return pharmacies
    }


    private fun getLastLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null){
                    val lat = location.latitude.toString()
                    val lng = location.longitude.toString()
                    getNearbyPharmacies("$lat,$lng");
                }
            }

        } else {
            askPermission()
        }
    }
    private fun askPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 999)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 999){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
            else {
                Toast.makeText(this, "Requiere permisos de ubicación", Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = ttsHelper.tts!!.setLanguage(Locale("es", "ES"))

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {  }
        }
    }
}