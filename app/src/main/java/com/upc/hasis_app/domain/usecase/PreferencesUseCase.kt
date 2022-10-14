package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.local.preferences.LocalPreferenceDataStore
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient
import com.upc.hasis_app.domain.entity.Schedule
import com.upc.hasis_app.domain.entity.Speciality
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

    fun getRole(): String? {
        return preferenceDataStore.getRole()
    }

    fun setRole(token: String) {
        preferenceDataStore.setRole(token)
    }

    fun getSpecialitySelected(): Speciality? {
        return preferenceDataStore.getSpecialitySelected()
    }

    fun setSpecialitySelected(speciality: Speciality) {
        preferenceDataStore.setSpecialitySelected(speciality)
    }


    fun getSchedules(): List<Schedule>? {
        return preferenceDataStore.getSchedules()
    }

    fun setSchedules(schedules: List<Schedule>) {
        preferenceDataStore.setSchedules(schedules)
    }

    fun getServiceStatus(): String? {
        return preferenceDataStore.getServiceStatus()
    }

    fun setServiceStatus(status: String) {
        preferenceDataStore.setServiceStatus(status)
    }

}