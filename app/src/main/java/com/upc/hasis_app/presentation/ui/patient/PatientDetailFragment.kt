package com.upc.hasis_app.presentation.ui.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentPatientDetailBinding
import com.upc.hasis_app.presentation.view_model.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientDetailFragment : Fragment() {

    private lateinit var binding: FragmentPatientDetailBinding
    private val viewModel: PatientDetailViewModel by activityViewModels()
    private val prescriptionViewModel : RegisterRecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientDetailBinding.inflate(inflater, container, false)

        initObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRecipeConsult.setOnClickListener {
            findNavController().navigate(R.id.go_to_recipe)
        }

        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.back_to_patient_consult)
        }
    }

    private fun initObservers(){
        viewModel.currentPatientState.observe(viewLifecycleOwner) {
            when (it) {
                is PatientStatus.PatientDataComplete -> {
                    binding.tvPatientName.text = viewModel.patient?.user!!.firstName + " " + viewModel.patient?.user!!.lastName
                    binding.tvPatientAge.text = "Edad: ${viewModel.patient?.user!!.age}"
                    binding.tvPatientHeight.text = "Grupo Sanguineo: ${viewModel.patient?.bloodT}"
                    binding.tvPatientWeight.text = "Alergias: ${viewModel.patient?.allergy}"
                }
                else -> {}
            }
        }
    }

}