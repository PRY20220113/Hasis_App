package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


sealed class WelcomeStatus {

    object Success : WelcomeStatus()

}

class WelcomeViewModel : ViewModel() {

    val currentState : MutableLiveData<WelcomeStatus> by lazy {
        MutableLiveData<WelcomeStatus>()
    }

    fun setState(value: WelcomeStatus) {
        currentState.postValue(value)
    }

}