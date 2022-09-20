package com.upc.hasis_app.data.local.preferences

import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient

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
}