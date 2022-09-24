package com.upc.hasis_app.presentation.view_model

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.upc.hasis_app.R
import com.upc.hasis_app.data.model.request.UpdateMedicineRequest
import com.upc.hasis_app.domain.entity.Speciality
import com.upc.hasis_app.domain.usecase.MedicineUseCase
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SpecialityViewModel  : ViewModel()  {


    lateinit var specialities : List<Speciality>
    var specialityId : Int? = null
    var updated = false

    val currentSpeakStatus : MutableLiveData<SpeakStatus> by lazy {
        MutableLiveData<SpeakStatus>()
    }

    fun setSpecialityList(newSpecialities : List<Speciality>) {
        specialities = newSpecialities
    }

    fun setSpecialityId(id : Int) {
        specialityId = id
    }

    fun setSpecialityStatus( status : SpeakStatus ) {
        currentSpeakStatus.postValue(status)
    }

    fun navigateTo(specialityText : String, navController : NavController) {

        var bundle : Bundle? = null

        for(speciality in specialities) {
            if(specialityText.contains(speciality.name.lowercase())){
                bundle = bundleOf("specialityId" to speciality.specialityId)
                break
            }
        }
        if(bundle != null) {
            navController.navigate(R.id.go_to_speciality_patient_recipes, bundle)
        }
    }

    fun interactWithUser(ttsHelper: TTSHelper) {

        var textToSpeak = ", uste tiene recetas por las siguientes especialidades, "


        for(it in specialities.indices) {
            if(it >= specialities.size - 1) textToSpeak += " y "
            textToSpeak += "${specialities[it].name}, "

        }

        textToSpeak += ". Para revisar el detalle de la receta por favor diga el nombre de la especialidad."

        ttsHelper.speakOut(textToSpeak)

    }

}