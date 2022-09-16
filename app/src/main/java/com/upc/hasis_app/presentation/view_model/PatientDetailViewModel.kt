package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upc.hasis_app.domain.entity.Patient
import com.upc.hasis_app.domain.usecase.PatientUseCase
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientDetailViewModel @Inject constructor(
    private val patientUseCase: PatientUseCase,
    private val preferencesUseCase: PreferencesUseCase)  : ViewModel() {

    val currentPatientState : MutableLiveData<PatientStatus> by lazy {
        MutableLiveData<PatientStatus>()
    }

    var patient: Patient? = null

    fun setPatientStatus(status : PatientStatus) {
        currentPatientState.postValue(status)
    }

    fun updatePatient(newPatient: Patient) {
        patient = newPatient
        setPatientStatus(PatientStatus.PatientDataComplete)
    }


    fun getPatientDetail(patientId : Int = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = patientUseCase.getPatientById(patientId)
            if (call.isSuccessful) {
                val responseDTO = call.body()
                if (responseDTO!!.httpCode == 200) {
                    val patient = responseDTO.data
                    updatePatient(patient!!)
                }
            }
        }
    }

}