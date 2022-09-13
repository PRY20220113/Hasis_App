package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upc.hasis_app.domain.entity.Patient


sealed class PatientStatus {

    object Success : PatientStatus()
    object Started : PatientStatus()
    object PatientDataComplete : PatientStatus()
    object SpeakComplete : PatientStatus()
    object Listening : PatientStatus()
    object ListenComplete : PatientStatus()
    object DataComplete : PatientStatus()
}


class PatientConsultVIewModel : ViewModel() {

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

}