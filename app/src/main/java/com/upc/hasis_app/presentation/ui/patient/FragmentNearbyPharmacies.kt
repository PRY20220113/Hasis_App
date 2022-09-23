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

//    private lateinit var binding : FragmentNearbyPharmaciesBinding
//    private lateinit var pharmacyAdapter: PharmacyAdapter
//    private var recyclerView : RecyclerView? = null
//
//    @Inject
//    lateinit var placesUseCase: PlacesUseCase
//
//    private var locationString = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentNearbyPharmaciesBinding.inflate(inflater, container, false)
//        recyclerView = binding.nearbyPharmaciesContainer
//        return binding.root
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val layoutManager = LinearLayoutManager(context)
//        recyclerView!!.layoutManager = layoutManager
//        binding.progressIndicator.visibility = View.VISIBLE
//        binding.nearbyPharmaciesContainer.visibility = View.GONE
//        binding.btnBack.setOnClickListener {
//            requireActivity().onBackPressed()
//        }
//        getActiveSpecialities()
//
//    }
//


}