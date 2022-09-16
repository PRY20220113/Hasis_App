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
import com.upc.hasis_app.databinding.FragmentRegisterRecipeBinding
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.presentation.adapter.MedicineAdapter
import com.upc.hasis_app.presentation.view_model.RegisterRecipeViewModel
import com.upc.hasis_app.presentation.view_model.RegisterStatus

class RegisterRecipeFragment : Fragment() {

    private lateinit var binding: FragmentRegisterRecipeBinding
    private lateinit var medicineAdapter : MedicineAdapter

    private val viewModel : RegisterRecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterRecipeBinding.inflate(inflater, container, false)

        val recyclerView = binding.prescriptionContainer

        medicineAdapter = MedicineAdapter(viewModel.medicines, viewModel)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = medicineAdapter

        initObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddPrescription.setOnClickListener {
            findNavController().navigate(R.id.go_to_add_prescription)
        }

        binding.btnRegisterRecipe.setOnClickListener {
            registerRecipe()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.back_to_recipe)
        }

    }

    private fun initObservers(){
        viewModel.registerStatus.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterStatus.Success -> {
                    findNavController().navigate(R.id.back_to_recipe)
                }
                is RegisterStatus.Failed -> {

                }
                else -> {}
            }
        }
    }

    private fun registerRecipe() {
        viewModel.registerRecipe()
    }


}