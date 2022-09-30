package com.upc.hasis_app.presentation.ui.medical_recipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.databinding.FragmentSpecialityRecipesBinding
import com.upc.hasis_app.domain.entity.Schedule
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import com.upc.hasis_app.presentation.adapter.PrescriptionPatientAdapter
import com.upc.hasis_app.presentation.ui.PatientRecipeSpecialityActivity
import com.upc.hasis_app.presentation.view_model.*
import com.upc.hasis_app.util.service.NotificationService
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
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
        getActiveRecipeForSpeciality()
        ttsHelper = (activity as PatientRecipeSpecialityActivity).ttsHelper

        binding.btnListen.setOnClickListener {
            viewModel.interactWithUser(ttsHelper)
        }

        binding.btnBack.setOnClickListener {
//            findNavController().navigate(
//                FragmentMedicalRecipeSpecialityDirections.backToRecipesSpecialities()
//            )
            ttsHelper.silence()
            viewModel.setState(SpeakStatus.SpeakComplete)
            requireActivity().onBackPressed()
        }

        binding.btnStart.setOnClickListener {
            preferencesUseCase.setSchedules(generateSchedules())
            requireActivity().startService(Intent(requireActivity(), NotificationService::class.java))
            //requireActivity().stopService(Intent(requireActivity(), NotificationService::class.java))
        }

        initObservers()

    }

    private fun generateSchedules(): List<Schedule> {
        val schedules = mutableListOf<Schedule>()
        for (medicine  in viewModel.medicines){
            val total = (medicine.prescribedDays * 24) / medicine.eachHour
            for (i in 0..total){
                schedules.add(Schedule(medicine.medicineId, medicine.name, medicine.weight, medicine.quantity, getNowDateRounded(i*medicine.eachHour).toString()))
            }
        }
        println(schedules.toString())
        return schedules
    }

    private fun getNowDateRounded(hours : Int): LocalDateTime {
        val nowDate = LocalDate.now().atTime(LocalDateTime.now().hour+1, 0)
       return nowDate.plusHours(hours.toLong())
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
                        viewModel.setState(SpeakStatus.ReadyToSpeak)
//                        specialityAdapter = SpecialityAdapter(responseDTO.data!!,navigation )
//                        recyclerView!!.adapter = specialityAdapter
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

