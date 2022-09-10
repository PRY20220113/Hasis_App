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
import com.google.zxing.integration.android.IntentIntegrator
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentPatientConsultBinding
import com.upc.hasis_app.databinding.FragmentProfileBinding
import com.upc.hasis_app.domain.usecase.PatientUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PatientConsultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class PatientConsultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var binding: FragmentPatientConsultBinding

    private lateinit var zxingActivityResultContracts: ActivityResultLauncher<Intent>

    @Inject
    lateinit var patientUseCase: PatientUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //initScanner()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientConsultBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initScanner(){
        zxingActivityResultContracts =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val intentResult = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
            if(intentResult.contents != null) {
                Toast.makeText(requireActivity(), intentResult.contents, Toast.LENGTH_LONG).show()
            }
        }
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

    var zxingActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)

        if (result.resultCode == Activity.RESULT_OK) {

            if (intentResult.contents == null) {
                Log.i("CódigoQR: ", "Cancelado" )
            } else {
                Log.i("CódigoQR: ", intentResult.contents)

                GlobalScope.launch(Dispatchers.IO){
                    Log.i("Paciente", patientUseCase.getPatientById(intentResult.contents.toInt()).body().toString())
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PatientConsultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PatientConsultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}