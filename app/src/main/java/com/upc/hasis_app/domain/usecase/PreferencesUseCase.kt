package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.local.preferences.LocalPreferenceDataStore
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient
import javax.inject.Inject

class PreferencesUseCase @Inject constructor(
    private val preferenceDataStore: LocalPreferenceDataStore
) {

     fun getLoginRequest(): LoginRequest? {
        return preferenceDataStore.getLoginRequest()
    }
     fun setLoginRequest(loginRequest: LoginRequest) {
        preferenceDataStore.setLoginRequest(loginRequest)
    }

     fun getUserDoctorLoggIn(): Doctor? {
        return preferenceDataStore.getUserDoctorLoggIn()
    }
     fun setUserDoctorLoggIn(doctor: Doctor){
        return preferenceDataStore.setUserDoctorLoggIn(doctor)
    }

    fun getUserPatientLoggIn(): Patient? {
        return preferenceDataStore.getUserPatientLoggIn()
    }
    fun setUserPatientLoggIn(patient:  Patient){
        return preferenceDataStore.setUserPatientLoggIn(patient)
    }

     fun getToken(): String? {
        return preferenceDataStore.getToken()
    }

     fun setToken(token: String) {
        preferenceDataStore.setToken(token)
    }



}