package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upc.hasis_app.data.model.response.CrearPacienteResponse


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


    var patient: CrearPacienteResponse? = null

    fun setPatientStatus(status : PatientStatus) {
        currentPatientState.postValue(status)
    }

    fun updatePatient(newPatient: CrearPacienteResponse) {
        patient = newPatient
        setPatientStatus(PatientStatus.PatientDataComplete)
    }

}