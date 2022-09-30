package com.upc.hasis_app.presentation.view_model

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.dynamic.SupportFragmentWrapper
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


    var specialities : List<Speciality> = arrayListOf()
    var specialityId : Int? = null
    var updated = false
    var cameFromRecipes = false

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

    fun navigateTo(specialityText : String, navController : NavController, goToProfile : Unit) {

        var bundle : Bundle? = null


        if(specialityText.contains("perfil")) {
            goToProfile
            setSpecialityStatus(SpeakStatus.Success)
        } else {
            for(speciality in specialities) {

                if(specialityText.contains(speciality.name.lowercase())){
                    bundle = bundleOf("specialityId" to speciality.specialityId)
                    break
                }
            }

            if(bundle != null) {
                cameFromRecipes = true
                navController.navigate(R.id.go_to_speciality_patient_recipes, bundle)
            } else {
                setSpecialityStatus(SpeakStatus.NotFound)
            }
        }
    }

    fun interactOnError(ttsHelper: TTSHelper) {
        var textToSpeak = "No se ha encontrado la especialidad mencionada. Por favor vuelva a repetirla"
        ttsHelper.speakOut(textToSpeak)
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