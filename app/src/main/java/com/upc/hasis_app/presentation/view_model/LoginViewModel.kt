package com.upc.hasis_app.presentation.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.upc.hasis_app.MainActivity
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import com.upc.hasis_app.domain.usecase.UserUseCase
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


sealed class UserActionStatus {

    object GetUser : UserActionStatus()
    object GetPassword : UserActionStatus()

}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val preferencesUseCase: PreferencesUseCase) : ViewModel() {

    val currentState : MutableLiveData<ResultStatus> by lazy {
        MutableLiveData<ResultStatus>()
    }

    var userName : String? = null
    var password : String? = null

    var errorMessage: String = ""

    fun setState(value: ResultStatus) {
        currentState.postValue(value)
    }

    fun dataComplete() : Boolean {
        return !userName.isNullOrEmpty()  && !password.isNullOrEmpty()
    }

    fun setData(dni : String, password : String) {
        userName = dni
        this.password = password
    }

    fun loginUser() {
        val loginRequest  = LoginRequest(dni = userName!!, password = password!!)
        val gson = Gson()
        CoroutineScope(Dispatchers.IO).launch {
            val call = userUseCase.login(loginRequest)
            if(call.isSuccessful) {
                val headers = call.headers()
                val responseDTO = call.body()
                if (responseDTO!!.errorCode == 0) {
                    Log.i("LoginResponse", responseDTO.toString() )
                    preferencesUseCase.setLoginRequest(loginRequest)
                    preferencesUseCase.setToken("Bearer ${headers.get("Token").toString()}")
                    if (headers.get("Rol").toString() == "D"){
                        preferencesUseCase.setUserDoctorLoggIn(gson.fromJson(gson.toJson(responseDTO.data), Doctor::class.java))
                        preferencesUseCase.setRole("D")
                        setState(ResultStatus.LoggedInDoctor)
                    } else {
                        preferencesUseCase.setUserPatientLoggIn(gson.fromJson(gson.toJson(responseDTO.data), Patient::class.java))
                        preferencesUseCase.setRole("P")
                        setState(ResultStatus.LoggedInPatient)
                    }
                } else {
                    errorMessage = responseDTO.errorMessage
                    Log.i("Error Body Login ", errorMessage )
                    setState(ResultStatus.FailedLoggedIn)
                }
            } else {
                errorMessage = call.errorBody()!!.string()
                Log.i("Error Body Login ", errorMessage )
                setState(ResultStatus.FailedLoggedIn)
                this.cancel()
            }
        }
    }

    fun getUserPreferences() {
        CoroutineScope(Dispatchers.IO).launch {
            val loginRequest =  preferencesUseCase.getLoginRequest();
            if(loginRequest != null) {
                userName = loginRequest.dni
                password = loginRequest.password
                setState(ResultStatus.PreferencesExist)
            }
        }
    }

    fun interactWithUser(action: UserActionStatus, ttsHelper: TTSHelper) {

        when(action) {
            is UserActionStatus.GetUser -> {
                setState(ResultStatus.Speaking)
                ttsHelper.speakOut("Ingrese su usuario")
                setState(ResultStatus.SpeakComplete)
            }
            is UserActionStatus.GetPassword -> {
                ttsHelper.speakOut("Ingrese su contraseña")
            }
        }


    }
}