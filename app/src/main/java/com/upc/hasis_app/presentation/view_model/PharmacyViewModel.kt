package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.domain.entity.Pharmacy
import com.upc.hasis_app.util.tts.TTSHelper

class PharmacyViewModel : ViewModel() {

    var pharmacies : List<Pharmacy> = arrayListOf()

    val currentState : MutableLiveData<SpeakStatus> by lazy {
        MutableLiveData<SpeakStatus>()
    }

    fun setState(value: SpeakStatus) {
        currentState.postValue(value)
    }

    fun updateMedicines(newPharmacies : List<Pharmacy>) {
        pharmacies = newPharmacies
    }

    fun interactWithUser(ttsHelper: TTSHelper) {


        var textToSpeak = "Las farmacias mas cercanas son "


        for(it in pharmacies.indices) {
            if(it >= pharmacies.size - 1) textToSpeak += " y "
            textToSpeak += "${pharmacies[it].name} que se encuentra a {${pharmacies[it].distance}]} metros"

        }

        textToSpeak += ". Para dirigirse a una de las opciones seleccione la parte superior, media o inferior de la pantalla"

        ttsHelper.speakOut(textToSpeak)
    }

}