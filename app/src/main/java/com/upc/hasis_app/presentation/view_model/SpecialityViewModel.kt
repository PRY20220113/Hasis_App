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

sealed class SpecialityStatus {

    object Init : SpecialityStatus()
    object Success : SpecialityStatus()
    object Updated : SpecialityStatus()
    object Failed : SpecialityStatus()

}

@HiltViewModel
class SpecialityViewModel @Inject constructor(
    private val medicineUseCase : MedicineUseCase,
    private val preferencesUseCase: PreferencesUseCase
)  : ViewModel()  {


    var specialityId : Int? = null
    var updated = false

    val currentSpecialityStatus : MutableLiveData<SpecialityStatus> by lazy {
        MutableLiveData<SpecialityStatus>()
    }

    fun setSpecialityId(id : Int) {
        specialityId = id
    }

    fun setSpecialityStatus( status : SpecialityStatus ) {
        currentSpecialityStatus.postValue(status)
    }

//    fun updateMedicine(eachHour : Int?, weight : Int?, quantity : Int?) {
//        CoroutineScope(Dispatchers.IO).launch {
//
//            val updateMedicineRequest = UpdateMedicineRequest(weight = weight!!, quantity = quantity!!, eachHour = eachHour!!)
//            val call = medicineUseCase.updateMedicine(medicineId = medicineId!!, updateMedicineRequest = updateMedicineRequest)
//            if(call.isSuccessful) {
//                val responseDTO = call.body()
//                if(responseDTO!!.httpCode == 200) {
//                    updated = true
//                    setSpecialityStatus(SpecialityStatus.Updated)
//                } else { setSpecialityStatus( SpecialityStatus.Failed ) }
//            }
//        }
//    }
}