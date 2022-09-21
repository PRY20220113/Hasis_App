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

}