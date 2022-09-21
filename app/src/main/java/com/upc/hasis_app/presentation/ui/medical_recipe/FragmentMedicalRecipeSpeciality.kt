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
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentMedicalRecipeBinding
import com.upc.hasis_app.databinding.FragmentSpecialityRecipesBinding
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import com.upc.hasis_app.presentation.adapter.PrescriptionAdapter
import com.upc.hasis_app.presentation.adapter.SpecialityAdapter
import com.upc.hasis_app.presentation.view_model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentMedicalRecipeSpeciality : Fragment() {

    private lateinit var binding: FragmentSpecialityRecipesBinding

    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    @Inject
    lateinit var recipeUseCase: RecipeUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecialityRecipesBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnListen.setOnClickListener {

        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.back_to_recipes_specialities)
        }

        binding.btnFind.setOnClickListener {
        }
        getActiveRecipeForSpeciality()
    }

    private fun getActiveRecipeForSpeciality(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = recipeUseCase.getActiveRecipesBySpecialityOfPatient(preferencesUseCase.getUserPatientLoggIn()!!.patientId, preferencesUseCase.getSpecialitySelected()!!.specialityId)
            if (call.isSuccessful){
                val responseDTO = call.body()
                requireActivity().runOnUiThread {
//                    hideProgressBar()
                    if(responseDTO!!.httpCode == 200){
//
//                        specialityAdapter = SpecialityAdapter(responseDTO.data!!,navigation )
//                        recyclerView!!.adapter = specialityAdapter
                    } else {
                        Log.i("Response", responseDTO.toString())
                    }
                }
            } else {
                val errorMessage = call.errorBody()!!.string()
                Log.i("Response", errorMessage)
                requireActivity().runOnUiThread {
//                    hideProgressBar()
                }
                this.cancel()
            }
        }
    }
}

