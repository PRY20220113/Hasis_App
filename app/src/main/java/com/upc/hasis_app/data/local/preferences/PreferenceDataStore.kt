package com.upc.hasis_app.data.local.preferences

import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient
import com.upc.hasis_app.domain.entity.Schedule
import com.upc.hasis_app.domain.entity.Speciality

interface PreferenceDataStore {

    fun getLoginRequest(): LoginRequest?;
    fun setLoginRequest(loginRequest: LoginRequest);

    fun getUserDoctorLoggIn(): Doctor?;
    fun setUserDoctorLoggIn(doctor: Doctor);

    fun getUserPatientLoggIn(): Patient?;
    fun setUserPatientLoggIn(patient: Patient);

    fun getToken(): String?;
    fun setToken( token: String);

    fun getRole(): String?;
    fun setRole( token: String);

    fun getSpecialitySelected(): Speciality?;
    fun setSpecialitySelected( speciality: Speciality);

    fun getSchedules(): List<Schedule>?
    fun setSchedules(schedules: List<Schedule>)

    fun getServiceStatus(): String?
    fun setServiceStatus(status: String)
}