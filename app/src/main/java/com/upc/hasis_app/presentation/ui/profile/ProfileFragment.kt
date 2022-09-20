package com.upc.hasis_app.presentation.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.upc.hasis_app.MainActivity
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentLoginBinding
import com.upc.hasis_app.databinding.FragmentProfileBinding
import com.upc.hasis_app.databinding.FragmentSelectRoleBinding
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.presentation.view_model.ResultStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ProfileFragment : Fragment() {


    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    private var profileId = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(preferencesUseCase.getRole().equals("P")){
            profileId = preferencesUseCase.getUserPatientLoggIn()!!.patientId.toString()
        } else {
            profileId = preferencesUseCase.getUserDoctorLoggIn()!!.doctorId.toString()
        }
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(
            profileId,
            BarcodeFormat.QR_CODE,
            450,
            450
        )

        binding.ivCodigoQR.setImageBitmap(bitmap);

        CoroutineScope(Dispatchers.IO).launch{
            //Log.i("Test", preferencesUseCase.getLoginRequest().toString())
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}