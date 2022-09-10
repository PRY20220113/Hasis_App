package com.upc.hasis_app.data.local.preferences

import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.LoginDoctorResponse

interface PreferenceDataStore {

    fun getLoginRequest(): LoginRequest?;
    fun setLoginRequest(loginRequest: LoginRequest);

    fun getUserDoctorLoggIn(): LoginDoctorResponse?;
    fun setUserDoctorLoggIn(doctorResponse: LoginDoctorResponse);

    fun getUserPatientLoggIn();
    fun setUserPatientLoggIn();

    fun getToken(): String?;
    fun setToken( token: String);
}