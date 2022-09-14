package com.upc.hasis_app.presentation.ui.medical_recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentRegisterPrescriptionBinding
import com.upc.hasis_app.databinding.FragmentRegisterRecipeBinding
import com.upc.hasis_app.databinding.FragmentUpdatePrescriptionBinding
import com.upc.hasis_app.presentation.ui.PatientDetailActivityArgs

class UpdatePrescriptionFragment : Fragment() {


    private lateinit var binding: FragmentUpdatePrescriptionBinding

    val args: UpdatePrescriptionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdatePrescriptionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvMedicine.text = args.medicineName
        //var days : String =
        binding.etDays.text = "${args.medicineDays} días"

        binding.etWeight.setText(args.medicineWeight.toString())
        binding.etHours.setText(args.medicineHour.toString())
        binding.etQuantity.setText(args.medicineQuantity.toString())


        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.back_to_recipe_view)
        }
    }




}