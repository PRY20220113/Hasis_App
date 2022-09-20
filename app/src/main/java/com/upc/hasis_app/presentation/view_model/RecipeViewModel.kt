package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.domain.entity.Recipe
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val preferencesUseCase: PreferencesUseCase) : ViewModel() {

    var actualRecipe : Recipe? = null
    private var patientId : Int? = null
    var medicines : MutableList<Medicine> = mutableListOf()
    val recipeStatus : MutableLiveData<RecipeStatus> by lazy {
        MutableLiveData<RecipeStatus>()
    }

    fun setRecipeStatus(status : RecipeStatus) {
        recipeStatus.postValue(status)
    }

    fun setPatientId(id : Int) {
        patientId = id
    }

    fun getActiveRecipe() {

        if(medicines.isNotEmpty()) medicines.clear()

        CoroutineScope(Dispatchers.IO).launch {
            val call = recipeUseCase.getActiveRecipesOfPatient(patientId!!)
            if(call.isSuccessful) {
                val responseDTO = call.body()
                if(responseDTO!!.httpCode == 200) {
//                    actualRecipe = responseDTO.data
//                    if(!actualRecipe?.medicines.isNullOrEmpty()) medicines.addAll(actualRecipe!!.medicines)
//                    setRecipeStatus(RecipeStatus.Success)
                } else { setRecipeStatus( RecipeStatus.Failed ) }
            }
        }
    }

}