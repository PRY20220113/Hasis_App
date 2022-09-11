package com.upc.hasis_app.presentation.ui.medical_recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentMedicalRecipeBinding
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.presentation.adapter.PrescriptionAdapter
import com.upc.hasis_app.presentation.view_model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class FragmentMedicalRecipe : Fragment() {

    private lateinit var binding: FragmentMedicalRecipeBinding
    private lateinit var prescriptionAdapter: PrescriptionAdapter

    private val viewModel : RegisterPrescriptionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicalRecipeBinding.inflate(inflater, container, false)

        val recyclerView = binding.prescriptionContainer

        prescriptionAdapter = PrescriptionAdapter(viewModel.medicines)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = prescriptionAdapter

        initObservers()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddPrescription.setOnClickListener {
            //viewModel.setPatientStatus(PatientStatus.Success)
            findNavController().navigate(R.id.go_to_register_prescription)
        }

        binding.btnRegisterRecipe.setOnClickListener {
            registerRecipe()
        }


    }

    private fun initObservers(){
        viewModel.registerStatus.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterStatus.Success -> {
                    findNavController().navigate(R.id.back_to_patient)
                }
                is RegisterStatus.Failed -> {

                }
                else -> {}
            }
        }
    }

    fun registerRecipe() {

        viewModel.registerRecipe()

    }


}