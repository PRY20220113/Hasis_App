package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class DoctorNavigationStatus {

    object Registering : DoctorNavigationStatus()
    object Success : DoctorNavigationStatus()

}


class DoctorViewModel : ViewModel() {

    val currentDoctorNavigationStatus : MutableLiveData<DoctorNavigationStatus> by lazy {
        MutableLiveData<DoctorNavigationStatus>()
    }

    fun setPatientStatus(status : DoctorNavigationStatus) {
        currentDoctorNavigationStatus.postValue(status)
    }

}