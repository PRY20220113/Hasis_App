package com.upc.hasis_app.presentation.ui.register

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.data.model.request.RegisterDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.databinding.FragmentRegisterDoctorBinding
import com.upc.hasis_app.domain.usecase.DoctorUseCase
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.UserUseCase
import com.upc.hasis_app.util.Constantes
import com.upc.hasis_app.util.ErrorDialog
import com.upc.hasis_app.util.SuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragmentDoctor : Fragment() {

    private lateinit var binding: FragmentRegisterDoctorBinding


    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    @Inject
    lateinit var userUseCase: UserUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        binding.etRepeatPassword.doAfterTextChanged {
                binding.etPassword.error = null
                binding.etRepeatPassword.error = null
        }
    }

    private fun registerDoctor(){
        var doctor = RegisterDoctorRequest()
        doctor.dni      = binding.etDni.text.toString()
        doctor.firstName  = binding.etName.text.toString()
        doctor.lastName  = binding.etSurname.text.toString()
        doctor.email    = binding.etEmail.text.toString()
        doctor.phone    = binding.etPhone.text.toString()
        doctor.password = binding.etPassword.text.toString()
        doctor.license = binding.etSfees.text.toString()
        doctor.sex = "M"
        doctor.birthDate = LocalDate.now().toString()


        if(binding.etPassword.text.toString() != binding.etRepeatPassword.text.toString()){
            binding.etPassword.error = "Las constraseñas no coinciden"
            binding.etRepeatPassword.error = "Las constraseñas no coinciden"
            return
        }




        CoroutineScope(Dispatchers.IO).launch {
            val call = userUseCase.registerDoctor(doctor)
            requireActivity().runOnUiThread {
                if (call.isSuccessful){
                    val responseDTO = call.body()
                    if(responseDTO!!.httpCode == 201){
                        Log.i("Response", responseDTO.toString())
                        showSuccessDialog("¡Registro exitoso!")
                    } else {
                        Log.i("Response", responseDTO.toString())
                        showErrorDialog(responseDTO.errorMessage)
                    }
                } else {
                    val error = call.errorBody()
                    Log.i("Response", error!!.string())
                    showErrorDialog(error.string())
                }
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