package com.upc.hasis_app.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.LoginDoctorResponse
import com.upc.hasis_app.util.Constantes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalPreferenceDataStore @Inject constructor(
   @ApplicationContext context: Context
): PreferenceDataStore{

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private val SHARED_PREF_NAME: String = Constantes.APP_NAME


    private val KEY_LOGIN_REQUEST: String = "LoginRequest"
    private val KEY_DOCTOR_LOG_IN: String = "DoctorLogIn"
    private val KEY_PATIENT_LOG_IN: String = "PatientLogin"
    private val KEY_TOKEN: String = "Token"

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun getLoginRequest(): LoginRequest? {
        val json = sharedPreferences.getString(KEY_LOGIN_REQUEST, "")
        if (json!!.isEmpty()) return null

        val gson = Gson()

        return gson.fromJson(json, LoginRequest::class.java)
    }

    override fun setLoginRequest(loginRequest: LoginRequest) {
        if (loginRequest != null) {
            editor = sharedPreferences.edit()
            val gson = Gson()
            editor.putString(KEY_LOGIN_REQUEST, gson.toJson(loginRequest))
            editor.commit()
        } else {
            sharedPreferences.edit().putString(KEY_LOGIN_REQUEST, "").commit()
        }
    }

    override fun getUserDoctorLoggIn(): LoginDoctorResponse? {
        val json = sharedPreferences.getString(KEY_DOCTOR_LOG_IN, "")
        if (json!!.isEmpty()) return null

        val gson = Gson()

        return gson.fromJson(json, LoginDoctorResponse::class.java)
    }

    override fun setUserDoctorLoggIn(doctorResponse: LoginDoctorResponse) {
        if (doctorResponse != null) {
            editor = sharedPreferences.edit()
            val gson = Gson()
            editor.putString(KEY_DOCTOR_LOG_IN, gson.toJson(doctorResponse))
            editor.commit()
        } else {
            sharedPreferences.edit().putString(KEY_DOCTOR_LOG_IN, "").commit()
        }
    }

    override fun getUserPatientLoggIn() {
        TODO("Not yet implemented")
    }

    override fun setUserPatientLoggIn() {
        TODO("Not yet implemented")
    }

    override fun getToken(): String? {
        val token = sharedPreferences.getString(KEY_TOKEN, "")
        if (token!!.isEmpty()) return null
        return token
    }

    override fun setToken(token: String) {
        if (token != null) {
            editor = sharedPreferences.edit()
            editor.putString(KEY_TOKEN, token)
            editor.commit()
        } else {
            sharedPreferences.edit().putString(KEY_TOKEN, "").commit()
        }
    }
}