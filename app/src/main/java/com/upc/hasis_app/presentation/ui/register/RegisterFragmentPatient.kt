package com.upc.hasis_app.presentation.ui.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.request.RegisterDoctorRequest
import com.upc.hasis_app.data.model.request.RegisterPatientRequest
import com.upc.hasis_app.databinding.FragmentRegisterPatientBinding
import com.upc.hasis_app.domain.entity.Speciality
import com.upc.hasis_app.domain.usecase.DoctorUseCase
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.UserUseCase
import com.upc.hasis_app.util.ErrorDialog
import com.upc.hasis_app.util.SuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragmentPatient : Fragment() {


    private lateinit var binding: FragmentRegisterPatientBinding


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
        binding = FragmentRegisterPatientBinding.inflate(inflater, container, false)
        return binding.root;
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePicker()
        initButtons()
        initSexSpinner()
        binding.etBirthDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun initSexSpinner() {
        val genres = arrayListOf<String>("Género...","Másculino", "Femenino")
        val spnSexAdapter = object :
            ArrayAdapter<String>(requireContext(),R.layout.spinner_item, genres){
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                if(position == 0) { view.setTextColor(Color.GRAY) }
                else {
                    //view.setTextColor(Color.RED)
                }
                return view
            }
        }
        binding.spnSex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val value = parent!!.getItemAtPosition(position).toString()
                if(value == genres[0]){
                    (view as TextView).setTextColor(Color.GRAY)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.spnSex.adapter = spnSexAdapter
    }

    private fun initButtons(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.go_back_to_select_rol)
        }

        binding.btnRegister.setOnClickListener {
            validateFields()
        }
        binding.etRepeatPassword.doAfterTextChanged {
            binding.etPassword.error = null
            binding.etRepeatPassword.error = null
        }
        binding.etBirthDate.doAfterTextChanged {
            binding.etBirthDate.error = null
        }
    }

    private fun validateFields(){
        if(binding.etDni.text.toString().isEmpty() && binding.etDni.text.isBlank()){
            binding.etDni.error = "Campo requerido"
            return
        }
        if(binding.etEmail.text.toString().isEmpty() && binding.etEmail.text.isBlank()){
            binding.etEmail.error = "Campo requerido"
            return
        }
        if(binding.etPassword.text.toString().isEmpty() && binding.etPassword.text.isBlank()){
            binding.etPassword.error = "Campo requerido"
            return
        }
        if(binding.etRepeatPassword.text.toString().isEmpty() && binding.etRepeatPassword.text.isBlank()){
            binding.etRepeatPassword.error = "Campo requerido"
            return
        }
        if(binding.etPassword.text.toString() != binding.etRepeatPassword.text.toString()){
            binding.etPassword.error = "Las constraseñas no coinciden"
            binding.etRepeatPassword.error = "Las constraseñas no coinciden"
            return
        }
        if(binding.etName.text.toString().isEmpty() && binding.etName.text.isBlank()){
            binding.etName.error = "Campo requerido"
            return
        }
        if(binding.etSurname.text.toString().isEmpty() && binding.etSurname.text.isBlank()){
            binding.etSurname.error = "Campo requerido"
            return
        }

        if(binding.etPhone.text.toString().isEmpty() && binding.etPhone.text.isBlank()){
            binding.etPhone.error = "Campo requerido"
            return
        }
        if(binding.spnSex.selectedItemPosition == 0){
            with(binding.spnSex.selectedView as TextView) {
                error = ""
                setTextColor(Color.RED)
                text = "Seleccione un género"
            }
            return
        }
        if(binding.etBirthDate.text.toString().isEmpty() && binding.etBirthDate.text.isBlank()){
            binding.etBirthDate.error = "Campo requerido"
            return
        }
        if(binding.etBloodType.text.toString().isEmpty() && binding.etBloodType.text.isBlank()){
            binding.etBloodType.error = "Campo requerido"
            return
        }
        if(binding.etChronicleDiseases.text.toString().isEmpty() && binding.etChronicleDiseases.text.isBlank()){
            binding.etChronicleDiseases.error = "Campo requerido"
            return
        }
        if(binding.etAllergies.text.toString().isEmpty() && binding.etAllergies.text.isBlank()){
            binding.etAllergies.error = "Campo requerido"
            return
        }
        registerPatient()
    }

    private fun registerPatient(){

        showProgressBar()
        var patient = RegisterPatientRequest()
        patient.dni      = binding.etDni.text.toString()
        patient.firstName  = binding.etName.text.toString()
        patient.lastName  = binding.etSurname.text.toString()
        patient.email    = binding.etEmail.text.toString()
        patient.phone    = binding.etPhone.text.toString()
        patient.password = binding.etPassword.text.toString()
        patient.sex = if(binding.spnSex.selectedItem.toString() == "Másculino") "M" else "F"
        patient.birthDate = binding.etBirthDate.text.toString()
        patient.bloodT = binding.etBloodType.text.toString()
        patient.chronicD = binding.etChronicleDiseases.text.toString()
        patient.allergy = binding.etAllergies.text.toString()



        CoroutineScope(Dispatchers.IO).launch {
            val call = userUseCase.registerPatient(patient)
            if (call.isSuccessful){
                val responseDTO = call.body()
                requireActivity().runOnUiThread {
                    hideProgressBar()
                    if(responseDTO!!.httpCode == 201){
                        Log.i("Response", responseDTO.toString())
                        showSuccessDialog("¡Registro exitoso!")
                    } else {
                        Log.i("Response", responseDTO.toString())
                        binding.progressBar.visibility = View.GONE
                        showErrorDialog(responseDTO.errorMessage)
                    }
                }
            } else {
                val errorMessage = call.errorBody()!!.string()
                Log.i("Response", errorMessage)
                requireActivity().runOnUiThread {
                    hideProgressBar()
                    showErrorDialog(errorMessage)
                }
                this.cancel()
            }
        }
    }


    private fun showErrorDialog(message: String){
        val dialog = ErrorDialog(requireContext(), message)
        dialog.requestWindowFeature(1)
        dialog.show()
    }

    private fun showSuccessDialog(message: String){
        val dialog = SuccessDialog(requireContext(), message)
        dialog.requestWindowFeature(1)
        dialog.setOnDismissListener {
            findNavController().navigate(R.id.back_to_login_from_patient_register)
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

    private fun blockUserInteraction(){
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun restoreUserInteraction(){
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar.requestFocus()
        blockUserInteraction()
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
        restoreUserInteraction()
    }


}