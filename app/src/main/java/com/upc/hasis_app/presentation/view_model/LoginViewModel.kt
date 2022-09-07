package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upc.hasis_app.MainActivity
import com.upc.hasis_app.util.tts.TTSHelper


sealed class UserActionStatus {

    object GetUser : UserActionStatus()
    object GetPassword : UserActionStatus()

}

class LoginViewModel : ViewModel() {

    val currentState : MutableLiveData<ResultStatus> by lazy {
        MutableLiveData<ResultStatus>()
    }

    var userName : String? = null
    var password : String? = null

    fun setState(value: ResultStatus) {
        currentState.postValue(value)
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