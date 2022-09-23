package com.upc.hasis_app.presentation.ui.patient

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.internal.LinkedTreeMap
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentDoctorSpecialitiesBinding
import com.upc.hasis_app.databinding.FragmentNearbyPharmaciesBinding
import com.upc.hasis_app.databinding.FragmentSpecialityRecipesBinding
import com.upc.hasis_app.domain.entity.Pharmacy
import com.upc.hasis_app.domain.usecase.PlacesUseCase
import com.upc.hasis_app.presentation.adapter.PharmacyAdapter
import com.upc.hasis_app.presentation.adapter.SpecialityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FragmentNearbyPharmacies : Fragment() {

    private lateinit var binding : FragmentNearbyPharmaciesBinding
    private lateinit var pharmacyAdapter: PharmacyAdapter
    private var recyclerView : RecyclerView? = null

    @Inject
    lateinit var placesUseCase: PlacesUseCase

    private var locationString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNearbyPharmaciesBinding.inflate(inflater, container, false)
        recyclerView = binding.nearbyPharmaciesContainer
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        binding.progressIndicator.visibility = View.VISIBLE
        binding.nearbyPharmaciesContainer.visibility = View.GONE
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        getActiveSpecialities()

    }

    private fun hideProgressBar(){
        binding.progressIndicator.visibility = View.GONE
        binding.nearbyPharmaciesContainer.visibility = View.VISIBLE
    }


    private fun getActiveSpecialities(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = placesUseCase.getNerbyPharmacies("-11.989413,-77.087313")
            if (call.isSuccessful){
                val body = call.body() as LinkedTreeMap<*, *>
                requireActivity().runOnUiThread {
                    hideProgressBar()
                    pharmacyAdapter = PharmacyAdapter(getMappedPharmacies(body).toList(),onClickPharmacyListener(),context!!)
                    recyclerView!!.adapter = pharmacyAdapter
                }
            } else {
                val errorMessage = call.errorBody()!!.string()
                Log.i("Response", errorMessage)
                requireActivity().runOnUiThread {
                    hideProgressBar()
                }
                this.cancel()
            }
        }
    }

    private fun getMappedPharmacies(map: LinkedTreeMap<*,*>): MutableList<Pharmacy> {
        val results = map["results"] as List<LinkedTreeMap<*,*>>
        val pharmacies = mutableListOf<Pharmacy>()
        for (pharmacy in results){
                pharmacies.add(Pharmacy(
                    pharmacy["place_id"].toString() ,
                    pharmacy["name"].toString(),
                    ((pharmacy["geometry"] as LinkedTreeMap<*,*>)["location"] as LinkedTreeMap<*,*>).let {
                        val lat =  it.get("lat").toString()
                        val lng = it.get("lng").toString()
                        "$lat,$lng"
                    }.toString() ))
        }
        return pharmacies
    }

    private fun onClickPharmacyListener() : View.OnClickListener {
        return View.OnClickListener {
            val gmmIntentUri = Uri.parse("google.navigation:w=Taronga+Zoo,+Sydney+Australia")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
        }
    }
}