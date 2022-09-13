package com.upc.hasis_app.presentation.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.upc.hasis_app.MainActivity
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import com.upc.hasis_app.domain.usecase.UserUseCase
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    fun setState(value: ResultStatus) {
        currentState.postValue(value)
    }

    fun dataComplete() : Boolean {
        return userName != null && password != null
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
            val headers = call.headers()
            if(call.isSuccessful) {
                val responseDTO = call.body()
                if (responseDTO!!.errorCode == 0) {
                    Log.i("LoginResponse", responseDTO.toString() )
                    preferencesUseCase.setLoginRequest(loginRequest)
                    preferencesUseCase.setToken("Bearer ${headers.get("Token").toString()}")
                    if (headers.get("Rol").toString() == "D"){
                        preferencesUseCase.setUserDoctorLoggIn(gson.fromJson(gson.toJson(responseDTO.data), Doctor::class.java))
                    } else {
                        preferencesUseCase.setUserDoctorLoggIn(gson.fromJson(gson.toJson(responseDTO.data), Doctor::class.java))
                    }
                    setState(ResultStatus.LoggedIn)
                } else {
                    val errorResponse = call.errorBody()!!.toString()
                    Log.i("Error Body Login ", errorResponse )
                }
            } else {

            }
        }
    }

    fun getUserPreferences() {
        GlobalScope.launch(Dispatchers.IO){
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