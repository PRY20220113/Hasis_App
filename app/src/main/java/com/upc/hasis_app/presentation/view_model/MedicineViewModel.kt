package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upc.hasis_app.data.model.request.UpdateMedicineRequest
import com.upc.hasis_app.domain.usecase.MedicineUseCase
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MedicineStatus {

    object Init : MedicineStatus()
    object Success : MedicineStatus()
    object Updated : MedicineStatus()
    object Failed : MedicineStatus()

}

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val medicineUseCase : MedicineUseCase ,
    private val preferencesUseCase: PreferencesUseCase)  : ViewModel()  {


    var medicineId : Int? = null
    var updated = false

    val currentMedicineStatus : MutableLiveData<MedicineStatus> by lazy {
        MutableLiveData<MedicineStatus>()
    }

    fun setMedicineId(id : Int) {
        medicineId = id
    }

    fun setMedicineStatus( status : MedicineStatus ) {
        currentMedicineStatus.postValue(status)
    }

    fun updateMedicine(eachHour : Int?, weight : Int?, quantity : Int?) {
        CoroutineScope(Dispatchers.IO).launch {

            val updateMedicineRequest = UpdateMedicineRequest(weight = weight!!, quantity = quantity!!, eachHour = eachHour!!)
            val call = medicineUseCase.updateMedicine(medicineId = medicineId!!, updateMedicineRequest = updateMedicineRequest)
            if(call.isSuccessful) {
                val responseDTO = call.body()
                if(responseDTO!!.httpCode == 200) {
                    updated = true
                    setMedicineStatus(MedicineStatus.Updated)
                } else { setMedicineStatus( MedicineStatus.Failed ) }
            }
        }
    }

}