package com.upc.hasis_app.presentation.ui.medical_recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.data.model.request.CreateMedicineRequest
import com.upc.hasis_app.databinding.FragmentRegisterPrescriptionBinding
import com.upc.hasis_app.presentation.view_model.PatientConsultVIewModel
import com.upc.hasis_app.presentation.view_model.RegisterPrescriptionViewModel

class PrescriptionRegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterPrescriptionBinding
    private val viewModel : RegisterPrescriptionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterPrescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            saveMedicineInPrescription()
            findNavController().navigate(R.id.back_to_recipes)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.back_to_recipes)
        }

    }


    private fun saveMedicineInPrescription() {

        val name = binding.etMedicine.text.toString()
        val weight = binding.etWeight.text.toString().toInt()
        val quantity = binding.etQuantity.text.toString().toInt()
        val eachHour = binding.etHours.text.toString().toInt()
        val prescribedDays = binding.etDays.text.toString().toInt()

        viewModel.addPrescription(CreateMedicineRequest(name = name, weight = weight, quantity = quantity, eachHour = eachHour, prescribedDays = prescribedDays))
    }


}