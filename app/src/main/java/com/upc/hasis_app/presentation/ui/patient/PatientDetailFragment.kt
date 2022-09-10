package com.upc.hasis_app.presentation.ui.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentPatientConsultBinding
import com.upc.hasis_app.databinding.FragmentPatientDetailBinding
import com.upc.hasis_app.presentation.view_model.PatientConsultVIewModel


class PatientDetailFragment : Fragment() {

    private lateinit var binding: FragmentPatientDetailBinding
    private val viewModel: PatientConsultVIewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPatientName.text = viewModel.patient?.name
        binding.tvPatientAge.text = "Edad: ${viewModel.patient?.age}"
        binding.tvPatientHeight.text = "Grupo Sanguineo: ${viewModel.patient?.bloodT}"
        binding.tvPatientWeight.text = "Alergias: ${viewModel.patient?.allergy}"


    }

}