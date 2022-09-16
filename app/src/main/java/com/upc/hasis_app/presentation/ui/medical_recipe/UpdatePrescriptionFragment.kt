package com.upc.hasis_app.presentation.ui.medical_recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentRegisterPrescriptionBinding
import com.upc.hasis_app.databinding.FragmentRegisterRecipeBinding
import com.upc.hasis_app.databinding.FragmentUpdatePrescriptionBinding
import com.upc.hasis_app.presentation.ui.PatientDetailActivityArgs
import com.upc.hasis_app.presentation.view_model.*

class UpdatePrescriptionFragment : Fragment() {


    private lateinit var binding: FragmentUpdatePrescriptionBinding
    private val viewModel : MedicineViewModel by activityViewModels()
    val args: UpdatePrescriptionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdatePrescriptionBinding.inflate(inflater, container, false)

        viewModel.setMedicineId(args.medicineId)

        initObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvMedicine.text = args.medicineName

        binding.etDays.text = "${args.medicineDays} días"

        binding.etWeight.setText(args.medicineWeight.toString())
        binding.etHours.setText(args.medicineHour.toString())
        binding.etQuantity.setText(args.medicineQuantity.toString())


        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.back_to_recipe_view)
        }

        binding.btnRegister.setOnClickListener {
            viewModel.updateMedicine(
                eachHour = binding.etHours.text.toString().toInt(),
                weight  = binding.etWeight.text.toString().toInt(),
                quantity = binding.etQuantity.text.toString().toInt(), )
        }
    }

    private fun initObservers(){
        viewModel.currentMedicineStatus.observe(viewLifecycleOwner) {
            when (it) {
                is MedicineStatus.Updated -> {
                    findNavController().navigate(R.id.back_to_recipe_view)
                }
                is MedicineStatus.Failed -> {

                }
                else -> {}
            }
        }
    }




}