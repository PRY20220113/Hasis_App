package com.upc.hasis_app.presentation.ui.pharmacy

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.internal.LinkedTreeMap
import com.upc.hasis_app.databinding.ActivityNearbyPharmaciesBinding
import com.upc.hasis_app.domain.entity.Pharmacy
import com.upc.hasis_app.domain.usecase.PlacesUseCase
import com.upc.hasis_app.presentation.adapter.PharmacyAdapter
import com.upc.hasis_app.presentation.view_model.*
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.apache.commons.text.WordUtils
import java.util.*
import javax.inject.Inject
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@AndroidEntryPoint
class PharmacyActivity : AppCompatActivity(), TextToSpeech.OnInitListener {


    private lateinit var binding: ActivityNearbyPharmaciesBinding
    private lateinit var pharmacyAdapter: PharmacyAdapter
    private var recyclerView : RecyclerView? = null
    lateinit var ttsHelper : TTSHelper
    private lateinit var context : Context
    private val viewModel : PharmacyViewModel by viewModels()
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    private var lat = 0.0
    private var lng = 0.0

    private var latP = 0.0
    private var lngP = 0.0

    private var containerHeight = 0

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
        recyclerView!!.layoutManager = object : LinearLayoutManager(this){
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp!!.height = (height /3.2).toInt()
                return true
            }
        }
        binding.progressIndicator.visibility = View.VISIBLE
        binding.nearbyPharmaciesContainer.visibility = View.INVISIBLE

        binding.btnBack.setOnClickListener {
            onBackPressed()
            finish();
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


    private fun getNearbyPharmacies(lat:Double, lng:Double){
        CoroutineScope(Dispatchers.IO).launch {
            val call = placesUseCase.getNerbyPharmacies("$lat,$lng")
            if (call.isSuccessful){
                val body = call.body() as LinkedTreeMap<*, *>
                runOnUiThread {
                    hideProgressBar()
                    val pharmacies = getMappedPharmacies(body).toList()
                    if (pharmacies.isNotEmpty()){
                        pharmacyAdapter = PharmacyAdapter(pharmacies, context)
                        recyclerView!!.adapter = pharmacyAdapter
                        binding.llNoNearbyPharmacies.visibility = View.INVISIBLE
                        binding.nearbyPharmaciesContainer.visibility = View.VISIBLE
                        viewModel.updateMedicines(pharmacies)
                        viewModel.setState(SpeakStatus.ReadyToSpeak)

                    } else {
                        binding.llNoNearbyPharmacies.visibility = View.VISIBLE
                        binding.nearbyPharmaciesContainer.visibility = View.INVISIBLE
                    }

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
                WordUtils.capitalizeFully(pharmacy["name"].toString()),
                ((pharmacy["geometry"] as LinkedTreeMap<*,*>)["location"] as LinkedTreeMap<*,*>).let {
                    latP =  it["lat"].toString().toDouble()
                    lngP = it["lng"].toString().toDouble()
                    "$latP,$lngP"
                }.toString(),getDistance(lat,lng, latP, lngP,'K').times(1000).toInt().toString() )
            )
            if (pharmacies.size >= 3) break
        }
        Log.i("Farmacias", pharmacies.toString())
        return pharmacies
    }


    private fun getLastLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null){
                     lat = location.latitude.toDouble()
                     lng = location.longitude.toDouble()
                    getNearbyPharmacies(lat,lng);
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

    private fun getDistance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
        unit: Char
    ): Double {
        val theta = lon1 - lon2
        var dist =
            sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(
                deg2rad(lat2)
            ) * cos(deg2rad(theta))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        if (unit == 'K') {
            dist *= 1.609344
        } else if (unit == 'N') {
            dist *= 0.8684
        }
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }
    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = ttsHelper.tts!!.setLanguage(Locale("es", "ES"))

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {  }
        }
    }

}