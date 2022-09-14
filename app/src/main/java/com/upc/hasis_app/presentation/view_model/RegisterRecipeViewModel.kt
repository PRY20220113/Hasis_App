package com.upc.hasis_app.presentation.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.data.model.request.CreateMedicineRequest
import com.upc.hasis_app.data.model.request.CreateRecipeRequest
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Recipe
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

sealed class RegisterStatus {

    object Registering : RegisterStatus()
    object Success : RegisterStatus()
    object Failed : RegisterStatus()

}

sealed class RecipeStatus {

    object Init : RecipeStatus()
    object Success : RecipeStatus()
    object Failed : RecipeStatus()

}

@HiltViewModel
class RegisterRecipeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val preferencesUseCase: PreferencesUseCase)  : ViewModel(){


    var medicinesToRegister : MutableList<CreateMedicineRequest> = mutableListOf()
    var medicines : MutableList<Medicine> = mutableListOf()
    val registerStatus : MutableLiveData<RegisterStatus> by lazy {
        MutableLiveData<RegisterStatus>()
    }
    val recipeStatus : MutableLiveData<RecipeStatus> by lazy {
        MutableLiveData<RecipeStatus>()
    }

    private var doctorId : Int? = null
    private var patientId : Int? = null

    fun setPatientId(id : Int) {
        patientId = id
    }

    fun setRecipeStatus(status : RecipeStatus) {
        recipeStatus.postValue(status)
    }



    fun addPrescription(medicineRequest: CreateMedicineRequest) {
        medicinesToRegister.add(medicineRequest)
        medicines.add(Medicine(
            0,
            medicineRequest.name, medicineRequest.weight, medicineRequest.quantity,
            medicineRequest.eachHour, medicineRequest.prescribedDays, "", ""))
    }

    fun setStatus(status: RegisterStatus) {
        registerStatus.postValue(status)
    }

     fun registerRecipe() {
        preferencesUseCase.getUserDoctorLoggIn()
            ?.let {
                doctorId = it.doctorId
            }

        CoroutineScope(Dispatchers.IO).launch {
            val call = recipeUseCase.createRecipe(CreateRecipeRequest(doctorId = doctorId!!, patientId = patientId!!, medicines = medicinesToRegister))

            if(call.isSuccessful) {
                val responseDTO = call.body()
                if(responseDTO!!.httpCode == 201) { setStatus(RegisterStatus.Success) }
                else { setStatus( RegisterStatus.Failed ) }
            }
        }
    }


}