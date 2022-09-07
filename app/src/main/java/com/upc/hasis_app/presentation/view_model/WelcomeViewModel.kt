package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


sealed class ResultStatus {

    object Success : ResultStatus()
    object Started : ResultStatus()
    object Speaking : ResultStatus()
    object SpeakComplete : ResultStatus()
    object Listening : ResultStatus()
    object ListenComplete : ResultStatus()
    object DataComplete : ResultStatus()
}

class WelcomeViewModel : ViewModel() {

    val currentState : MutableLiveData<ResultStatus> by lazy {
        MutableLiveData<ResultStatus>()
    }

    fun setState(value: ResultStatus) {
        currentState.postValue(value)
    }

}