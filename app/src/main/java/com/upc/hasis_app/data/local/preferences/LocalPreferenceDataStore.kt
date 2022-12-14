package com.upc.hasis_app.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient
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

    override fun getUserDoctorLoggIn(): Doctor? {
        val json = sharedPreferences.getString(KEY_DOCTOR_LOG_IN, "")
        if (json!!.isEmpty()) return null

        val gson = Gson()

        return gson.fromJson(json, Doctor::class.java)
    }

    override fun setUserDoctorLoggIn(doctor: Doctor) {
        if (doctor != null) {
            editor = sharedPreferences.edit()
            val gson = Gson()
            editor.putString(KEY_DOCTOR_LOG_IN, gson.toJson(doctor))
            editor.commit()
        } else {
            sharedPreferences.edit().putString(KEY_DOCTOR_LOG_IN, "").commit()
        }
    }

    override fun getUserPatientLoggIn(): Patient? {
        val json = sharedPreferences.getString(KEY_PATIENT_LOG_IN, "")
        if (json!!.isEmpty()) return null

        val gson = Gson()

        return gson.fromJson(json, Patient::class.java)
    }

    override fun setUserPatientLoggIn(patient: Patient)    {
        if (patient != null) {
            editor = sharedPreferences.edit()
            val gson = Gson()
            editor.putString(KEY_DOCTOR_LOG_IN, gson.toJson(patient))
            editor.commit()
        } else {
            sharedPreferences.edit().putString(KEY_PATIENT_LOG_IN, "").commit()
        }
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