package com.upc.hasis_app.presentation.ui.medical_recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentMedicalRecipeBinding
import com.upc.hasis_app.presentation.adapter.PrescriptionAdapter
import com.upc.hasis_app.presentation.view_model.*

class FragmentMedicalRecipe : Fragment() {

    private lateinit var binding: FragmentMedicalRecipeBinding
    private lateinit var prescriptionAdapter: PrescriptionAdapter

    private val viewModel : RecipeViewModel by activityViewModels()

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

        if(viewModel.actualRecipe == null) viewModel.getActiveRecipe()

        initObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterRecipe.setOnClickListener {
            //viewModel.setPatientStatus(PatientStatus.Success)
            findNavController().navigate(R.id.go_to_register_recipe)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.back_to_patient)
        }


    }

    private fun initObservers(){
        viewModel.recipeStatus.observe(viewLifecycleOwner) {
            when (it) {
                is RecipeStatus.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.prescriptionContainer.visibility = View.VISIBLE
                }
                is RecipeStatus.Failed -> {
                    binding.progressIndicator.visibility = View.GONE
                }
                else -> {  viewModel.getActiveRecipe() }
            }
        }
    }


}