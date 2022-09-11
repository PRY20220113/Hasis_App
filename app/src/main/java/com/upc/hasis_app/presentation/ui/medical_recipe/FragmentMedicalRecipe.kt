package com.upc.hasis_app.presentation.ui.medical_recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentMedicalRecipeBinding
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.presentation.adapter.PrescriptionAdapter
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class FragmentMedicalRecipe : Fragment() {

    private lateinit var binding: FragmentMedicalRecipeBinding
    private val prescriptions = ArrayList<Medicine>()
    private lateinit var prescriptionAdapter: PrescriptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicalRecipeBinding.inflate(inflater, container, false)

        val recyclerView = binding.prescriptionContainer

        prescriptions.add(Medicine(1, "Paracetamol", 10, 1, 8, 12,"2022-05-22", "2022-05-28"))

        prescriptionAdapter = PrescriptionAdapter(prescriptions)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = prescriptionAdapter

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddPrescription.setOnClickListener {
            //viewModel.setPatientStatus(PatientStatus.Success)
            findNavController().navigate(R.id.go_to_register_prescription)
        }
    }



}