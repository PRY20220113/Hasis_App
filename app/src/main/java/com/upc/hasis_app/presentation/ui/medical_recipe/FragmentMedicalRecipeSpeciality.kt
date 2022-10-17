package com.upc.hasis_app.presentation.ui.medical_recipe

import android.content.ContextWrapper
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentSpecialityRecipesBinding
import com.upc.hasis_app.domain.entity.Schedule
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import com.upc.hasis_app.presentation.adapter.PrescriptionPatientAdapter
import com.upc.hasis_app.presentation.ui.PatientRecipeSpecialityActivity
import com.upc.hasis_app.presentation.view_model.*
import com.upc.hasis_app.util.service.Actions
import com.upc.hasis_app.util.service.NotificationService
import com.upc.hasis_app.util.service.ServiceState
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject


@AndroidEntryPoint
class FragmentMedicalRecipeSpeciality : Fragment() {

    private lateinit var binding: FragmentSpecialityRecipesBinding

    private lateinit var recipePatientAdapter: PrescriptionPatientAdapter
    private val viewModel : RecipeListenViewModel by activityViewModels()
    private var recyclerView : RecyclerView? = null
    private lateinit var ttsHelper: TTSHelper

    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    @Inject
    lateinit var recipeUseCase: RecipeUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecialityRecipesBinding.inflate(inflater, container, false)

        recyclerView = binding.prescriptionPatientContainer

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        binding.progressIndicator.visibility = View.VISIBLE
        binding.prescriptionPatientContainer.visibility = View.GONE

        binding.specialityName.text = preferencesUseCase.getSpecialitySelected()!!.name

        ttsHelper = (activity as PatientRecipeSpecialityActivity).ttsHelper



        binding.btnListen.setOnClickListener {
            viewModel.interactWithUser(ttsHelper)
        }

        binding.btnBack.setOnClickListener {
            ttsHelper.silence()
            viewModel.setState(SpeakStatus.SpeakComplete)
            requireActivity().onBackPressed()
            requireActivity().finish()
        }

        binding.btnStart.setOnClickListener {
            preferencesUseCase.setSchedules(generateSchedules())
            //requireActivity().startService(Intent(requireActivity(), NotificationService::class.java))
            actionOnService(Actions.START)
            changeButtonStart()
            //requireActivity().stopService(Intent(requireActivity(), NotificationService::class.java))
        }
        getActiveRecipeForSpeciality()
        initObservers()
    }

    private fun actionOnService(action: Actions) {
        if (preferencesUseCase.getServiceStatus() == ServiceState.STOPPED.name && action == Actions.STOP) return
        Intent(requireActivity(), NotificationService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i("MEDICAL RECIPE SPECIALITY","Starting the service in >=26 Mode")
                requireActivity().startForegroundService(it)
                return
            }
            Log.i("MEDICAL RECIPE SPECIALITY","Starting the service in < 26 Mode")
           requireActivity().startService(it)
        }
    }


    private fun generateSchedules(): List<Schedule> {
        val schedules = mutableListOf<Schedule>()
        for (medicine  in viewModel.medicines){
            val total = (medicine.prescribedDays * 24) / medicine.eachHour
            for (i in 0..total){
                schedules.add(Schedule(medicine.medicineId, medicine.name, medicine.weight, medicine.quantity, getNowDateRounded(i).toString()))
            }
        }
        return schedules
    }

    private fun getNowDateRounded(hours : Int): LocalDateTime {
//        val nowDate = LocalDate.now().atTime(LocalDateTime.now().hour+1, 0)
//        return nowDate.plusHours(hours.toLong())
            val nowDate = LocalDate.now().atTime(LocalDateTime.now().hour, LocalDateTime.now().minute+1)
             return nowDate.plusMinutes(hours.toLong())
    }


    private fun changeButtonStart(){
//        binding.btnStart.text = "Medicación Iniciada"
//        binding.btnStart.isClickable = false
//        binding.btnStart.icon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_clock)
        binding.btnStart.visibility = View.GONE
    }

    private fun verifyMedicines(): Boolean{
        if (preferencesUseCase.getSchedules() == null) return false
        val medicines = mutableSetOf<Int>()
        val medicinesAux = mutableSetOf<Int>()
        for(schedule in preferencesUseCase.getSchedules()!!){
            medicines.add(schedule.medicineId)
        }
        for(medicine in viewModel.medicines){
            medicinesAux.add(medicine.medicineId)
        }
        println(medicinesAux)
        println(medicines)
        if (medicinesAux == medicines) return true
        return false
    }




    private fun initObservers(){
        viewModel.currentState.observe(viewLifecycleOwner) {
            when (it) {
                is SpeakStatus.ReadyToSpeak -> {

                    viewModel.interactWithUser(ttsHelper)
                }
                else -> {
                }
            }
        }
    }

    private fun hideProgressBar(){
        binding.progressIndicator.visibility = View.GONE
        binding.prescriptionPatientContainer.visibility = View.VISIBLE
    }

    private fun getActiveRecipeForSpeciality(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = recipeUseCase.getActiveRecipesBySpecialityOfPatient(preferencesUseCase.getUserPatientLoggIn()!!.patientId, preferencesUseCase.getSpecialitySelected()!!.specialityId)
            if (call.isSuccessful){
                val responseDTO = call.body()
                requireActivity().runOnUiThread {
                    hideProgressBar()
                    if(responseDTO!!.httpCode == 200){
                        recyclerView!!.adapter = PrescriptionPatientAdapter(responseDTO.data!!.medicines)
                        viewModel.updateMedicines(responseDTO.data!!.medicines)
                        if(verifyMedicines()){
                            changeButtonStart()
                        }
                        viewModel.setState(SpeakStatus.ReadyToSpeak)
                    } else {
                        Log.i("Response", responseDTO.toString())
                    }
                }
            } else {
                val errorMessage = call.errorBody()!!.string()
                Log.i("Response", errorMessage)
                requireActivity().runOnUiThread {
                    hideProgressBar()
                }
                this.cancel()
            }
        }
    }
}

