package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.lifecycle.HiltViewModel


sealed class SpeakStatus {

    object Success : SpeakStatus()
    object ReadyToSpeak : SpeakStatus()
    object Speaking : SpeakStatus()
    object SpeakComplete : SpeakStatus()

}

class RecipeListenViewModel : ViewModel() {

    lateinit var medicines : List<Medicine>

    val currentState : MutableLiveData<SpeakStatus> by lazy {
        MutableLiveData<SpeakStatus>()
    }

    fun setState(value: SpeakStatus) {
        currentState.postValue(value)
    }

    fun updateMedicines(newMedicines : List<Medicine>) {
        medicines = newMedicines
    }

    fun interactWithUser(ttsHelper: TTSHelper) {


        var textToSpeak = "Su receta indica que debe tomar "


        for(it in medicines.indices) {

            val quantity = medicines[it].quantity
            val tomas = if(quantity > 1) "tomas" else "toma"
            if(it >= medicines.size - 1) textToSpeak += " y "
            textToSpeak += "${medicines[it].name}, $quantity $tomas de ${medicines[it].weight} miligramos cada ${medicines[it].eachHour} horas por ${medicines[it].prescribedDays} días, "

        }

        ttsHelper.speakOut(textToSpeak)

        setState(SpeakStatus.SpeakComplete)
    }

}