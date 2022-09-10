package com.upc.hasis_app.presentation.ui.patient

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentPatientConsultBinding
import com.upc.hasis_app.databinding.FragmentProfileBinding
import com.upc.hasis_app.domain.usecase.PatientUseCase
import com.upc.hasis_app.presentation.view_model.PatientConsultVIewModel
import com.upc.hasis_app.presentation.view_model.PatientStatus
import com.upc.hasis_app.presentation.view_model.ResultStatus
import com.upc.hasis_app.presentation.view_model.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PatientConsultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var binding: FragmentPatientConsultBinding
    private val viewModel: PatientConsultVIewModel by activityViewModels()
    private lateinit var zxingActivityResultContracts: ActivityResultLauncher<Intent>

    @Inject
    lateinit var patientUseCase: PatientUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientConsultBinding.inflate(inflater, container, false)
        initObservers()
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode,  data)
        if (result != null){
            if(result.contents == null){
                //Toast.makeText(requireActivity(),"Cancelado", Toast.LENGTH_SHORT).show()
                Log.i("CódigoQR: ", "Cancelado" )
            } else {
                //Toast.makeText(requireActivity(),"El valor es: ${result.contents} " , Toast.LENGTH_SHORT).show()
                Log.i("CódigoQR: ", result.contents )
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnQRConsult.setOnClickListener {
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            integrator.setPrompt("Enfoque el código QR")
            integrator.setOrientationLocked(false)
            //integrator.setTorchEnabled(true)
            integrator.setBeepEnabled(true)
            //integrator.initiateScan()
            zxingActivityResultLauncher.launch(integrator.createScanIntent())
        }

    }


    private fun initObservers(){
        viewModel.currentPatientState.observe(viewLifecycleOwner) {
            when (it) {
                is PatientStatus.PatientDataComplete -> {
                    findNavController().navigate(R.id.load_patient)
                }
            }
        }
    }

    var zxingActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)

        if (result.resultCode == Activity.RESULT_OK) {

            if (intentResult.contents == null) {
                Log.i("CódigoQR: ", "Cancelado" )
            } else {
                Log.i("CódigoQR: ", intentResult.contents)

                GlobalScope.launch(Dispatchers.IO){
                    patientUseCase.getPatientById(intentResult.contents.toInt()).body()
                        ?.let { viewModel.updatePatient(it) }
                }

            }
        }
    }
}