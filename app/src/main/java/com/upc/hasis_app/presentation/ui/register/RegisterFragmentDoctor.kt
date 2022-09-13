package com.upc.hasis_app.presentation.ui.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
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
import kotlinx.coroutines.*
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragmentDoctor : Fragment() {

    private lateinit var binding: FragmentRegisterDoctorBinding

    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    @Inject
    lateinit var userUseCase: UserUseCase


    private lateinit var datePickerDialog: DatePickerDialog;

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
        initDatePicker()
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
        val generos = arrayListOf<String>("Másculino", "Femenino")
        val adapter = ArrayAdapter<String>(requireContext(),R.layout.spinner_item, generos)
        binding.spnSex.adapter = adapter
        binding.etBirthDate.setOnClickListener {
            showDatePicker()
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
        doctor.sex = if(binding.spnSex.selectedItem.toString() == "Másculino") "M" else "F"
        doctor.birthDate = binding.etBirthDate.text.toString()


        if(binding.etPassword.text.toString() != binding.etRepeatPassword.text.toString()){
            binding.etPassword.error = "Las constraseñas no coinciden"
            binding.etRepeatPassword.error = "Las constraseñas no coinciden"
            return
        }




        CoroutineScope(Dispatchers.IO).launch {
            val call = userUseCase.registerDoctor(doctor)
            if (call.isSuccessful){
            requireActivity().runOnUiThread {
                    val responseDTO = call.body()
                    if(responseDTO!!.httpCode == 201){
                        Log.i("Response", responseDTO.toString())
                        showSuccessDialog("¡Registro exitoso!")
                    } else {
                        Log.i("Response", responseDTO.toString())
                        showErrorDialog(responseDTO.errorMessage)
                    }
                }
            } else {
                val errorMessage = call.errorBody()!!.string()
                Log.i("Response", errorMessage)
                requireActivity().runOnUiThread {
                    showErrorDialog(errorMessage)
                }
                this.cancel()
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

    private fun initDatePicker(){
        val dataSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val date = makeDateString(dayOfMonth, month + 1, year)
            binding.etBirthDate.setText(date)
        }

        val calendar = Calendar.getInstance()

        datePickerDialog = DatePickerDialog(requireContext(), AlertDialog.THEME_HOLO_LIGHT ,dataSetListener,
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH))
    }

    private fun makeDateString(day:Int, month:Int, year:Int):String{
        val monthFor = if (month<10) "0$month" else month
        val dayForm = if (day<10) "0$day" else day

        return "$year-$monthFor-$dayForm"
    }

    private fun showDatePicker(){
        datePickerDialog.show()
    }

}