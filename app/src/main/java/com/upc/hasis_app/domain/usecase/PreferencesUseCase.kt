package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.local.preferences.LocalPreferenceDataStore
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.CrearPacienteResponse
import com.upc.hasis_app.data.model.response.LoginDoctorResponse
import retrofit2.Response
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
     fun getUserDoctorLoggIn(): LoginDoctorResponse? {
        return preferenceDataStore.getUserDoctorLoggIn()
    }
     fun setUserDoctorLoggIn(loginDoctorResponse: LoginDoctorResponse){
        return preferenceDataStore.setUserDoctorLoggIn(loginDoctorResponse)
    }

     fun getToken(): String? {
        return preferenceDataStore.getToken()
    }

     fun setToken(token: String) {
        preferenceDataStore.setToken(token)
    }



}