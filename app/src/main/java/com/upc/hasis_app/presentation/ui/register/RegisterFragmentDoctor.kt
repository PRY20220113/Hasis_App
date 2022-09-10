package com.upc.hasis_app.presentation.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.databinding.FragmentRegisterDoctorBinding
import com.upc.hasis_app.domain.usecase.DoctorUseCase
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.util.Constantes
import com.upc.hasis_app.util.ErrorDialog
import com.upc.hasis_app.util.SuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class RegisterFragmentDoctor : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentRegisterDoctorBinding


    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    @Inject
    lateinit var doctorUseCase: DoctorUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterDoctorBinding.inflate(inflater, container, false)
        return binding.root;
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.go_back_to_select_rol)
        }

        binding.btnRegister.setOnClickListener {
            registerDoctor()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




    private fun registerDoctor(){
        var doctor = CrearDoctorRequest()
        doctor.dni      = binding.etDni.text.toString()
        doctor.first_name  = binding.etName.text.toString()
        doctor.last_name  = binding.etSurname.text.toString()
        doctor.email    = binding.etEmail.text.toString()
        doctor.phone    = binding.etPhone.text.toString()
        doctor.password = binding.etPassword.text.toString()
        doctor.sfeesNum = binding.etSfees.text.toString()
        doctor.roles.add(Constantes.ROL_DOCTOR)

        GlobalScope.launch(Dispatchers.Main) {
            val response = doctorUseCase.createDoctor(doctor)
            Log.i("Response", response.toString())
            if(response.code() == 200){
                Log.i("Response", response.body().toString())
                showSuccessDialog("¡Registro exitoso!")
            } else {
                val error = response.errorBody()
                Log.i("Response", error!!.string())
                showErrorDialog(error.string())
            }
        }
    }

    private fun showErrorDialog(message: String){
        val dialog = ErrorDialog(requireContext(), message)
        dialog.requestWindowFeature(1)
        //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun showSuccessDialog(message: String){
        val dialog = SuccessDialog(requireContext(), message)
        dialog.requestWindowFeature(1)
        //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setOnDismissListener {
            findNavController().navigate(R.id.back_to_login_from_doctor_register)


            preferencesUseCase.setLoginRequest(LoginRequest("",""))

        }
        dialog.show()

    }

}