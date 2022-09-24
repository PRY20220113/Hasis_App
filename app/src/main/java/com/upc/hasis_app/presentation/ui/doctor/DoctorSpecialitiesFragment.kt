package com.upc.hasis_app.presentation.ui.doctor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentDoctorSpecialitiesBinding
import com.upc.hasis_app.databinding.FragmentPatientConsultBinding
import com.upc.hasis_app.domain.usecase.DoctorUseCase
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import com.upc.hasis_app.presentation.adapter.PrescriptionAdapter
import com.upc.hasis_app.presentation.adapter.SpecialityAdapter
import com.upc.hasis_app.presentation.ui.PatientActivity
import com.upc.hasis_app.presentation.view_model.RecipeListenViewModel
import com.upc.hasis_app.presentation.view_model.ResultStatus
import com.upc.hasis_app.presentation.view_model.SpeakStatus
import com.upc.hasis_app.presentation.view_model.SpecialityViewModel
import com.upc.hasis_app.util.stt.STTHelper
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DoctorSpecialitiesFragment : Fragment() {


    private lateinit var binding : FragmentDoctorSpecialitiesBinding
    private lateinit var specialityAdapter : SpecialityAdapter
    private var recyclerView : RecyclerView? = null
    private val viewModel : SpecialityViewModel by activityViewModels()
    private lateinit var ttsHelper: TTSHelper
    private lateinit var sttHelper : STTHelper

    @Inject
    lateinit var recipeUseCase: RecipeUseCase

    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    private lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("DoctorSpecialitiesFragment", "OnCreate")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("DoctorSpecialitiesFragment", "onCreateView")
        ttsHelper = (activity as PatientActivity).ttsHelper
        binding = FragmentDoctorSpecialitiesBinding.inflate(inflater, container, false)
        recyclerView = binding.specialitiesContainer
        getActiveSpecialities()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        binding.progressIndicator.visibility = View.VISIBLE
        binding.specialitiesContainer.visibility = View.GONE
        val parent = parentFragment
        sttHelper = STTHelper(context)
        navigation = findNavController()
        initObservers()
        setSpeechRecognizerListeners()
    }

    private fun setSpeechRecognizerListeners() {
        sttHelper.setSpeechRecognizerListeners(checkValueVal, goToSpecialityListen)
    }

    private val checkValueVal : () -> Unit = {

    }

    private val goToSpecialityListen : (ArrayList<String>) -> Unit = {
        viewModel.navigateTo(it[0].replace("\\s".toRegex(), ""), navigation)
    }


    private fun initObservers(){
        viewModel.currentSpeakStatus.observe(viewLifecycleOwner) {
            when (it) {
                is SpeakStatus.ReadyToSpeak -> {
                    viewModel.interactWithUser(ttsHelper)
                }
                is SpeakStatus.SpeakComplete -> {
                    sttHelper.listen()
                }
                else -> {
                }
            }
        }


        ttsHelper.currentState.observe(viewLifecycleOwner) {
            when(it) {
                is ResultStatus.SpeakComplete -> {
                    viewModel.setSpecialityStatus(SpeakStatus.SpeakComplete)
                }
                else -> {

                }
            }
        }
    }


    private fun hideProgressBar(){
        binding.progressIndicator.visibility = View.GONE
        binding.specialitiesContainer.visibility = View.VISIBLE
    }

    private fun getActiveSpecialities(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = recipeUseCase.getActiveSpecialityOfPatient(preferencesUseCase.getUserPatientLoggIn()!!.patientId)
            if (call.isSuccessful){
                val responseDTO = call.body()
                requireActivity().runOnUiThread {
                    hideProgressBar()
                    if(responseDTO!!.httpCode == 200){
                        specialityAdapter = SpecialityAdapter(responseDTO.data!!,navigation )
                        recyclerView!!.adapter = specialityAdapter
                        viewModel.setSpecialityList(responseDTO.data!!)
                        viewModel.setSpecialityStatus(SpeakStatus.ReadyToSpeak)
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