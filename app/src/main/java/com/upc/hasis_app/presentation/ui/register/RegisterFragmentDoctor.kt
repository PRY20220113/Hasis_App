package com.upc.hasis_app.presentation.ui.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.R
import com.upc.hasis_app.data.model.request.RegisterDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.databinding.FragmentRegisterDoctorBinding
import com.upc.hasis_app.domain.entity.Speciality
import com.upc.hasis_app.domain.usecase.DoctorUseCase
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.UserUseCase
import com.upc.hasis_app.presentation.adapter.SpecialitySpinnerAdapter
import com.upc.hasis_app.util.ErrorDialog
import com.upc.hasis_app.util.SuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragmentDoctor : Fragment() {

    private lateinit var binding: FragmentRegisterDoctorBinding

    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    @Inject
    lateinit var userUseCase: UserUseCase

    @Inject
    lateinit var doctorUseCase: DoctorUseCase


    private lateinit var datePickerDialog: DatePickerDialog;
    private lateinit var specialitySelected : Speciality
    private lateinit var genreSelected : String

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
        initButtons()
        initSexSpinner()
        initSpecialitySpinner()
        binding.etBirthDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun initSpecialitySpinner() {

        binding.spnSpeciality.adapter = ArrayAdapter<String>(requireContext(), R.layout.spinner_item, arrayListOf("Obteniendo Especialidades..."))
        CoroutineScope(Dispatchers.IO).launch {
            val call = doctorUseCase.getSpecialities()
            if (call.isSuccessful){
                val responseDTO = call.body()
                if(responseDTO!!.httpCode == 200){
                    val specialities : List<Speciality> = responseDTO.data!!
                    val auxSpecialities: MutableList<Speciality> = mutableListOf(Speciality(0,"Especialidades...",""))
                    auxSpecialities.addAll(specialities);
                    requireActivity().runOnUiThread {
                        val spnSpecialitiesAdapter = SpecialitySpinnerAdapter(requireContext(),R.layout.spinner_item, auxSpecialities)

                        binding.spnSpeciality.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                val value = parent!!.getItemAtPosition(position)
                                if(value == auxSpecialities[0]){
                                    (view as TextView).setTextColor(Color.GRAY)
                                } else {
                                    specialitySelected = value as Speciality
                                }
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
                        }
                        binding.spnSpeciality.adapter = spnSpecialitiesAdapter
                    }

                }
            }
        }
    }

    private fun initSexSpinner() {
        val genres = arrayListOf<String>("Género...","Másculino", "Femenino")
        val spnSexAdapter = object :ArrayAdapter<String>(requireContext(),R.layout.spinner_item, genres){
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
                genreSelected = value
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
        if(binding.spnSpeciality.selectedItemPosition == 0){
            with(binding.spnSpeciality.selectedView as TextView) {
                error = ""
                setTextColor(Color.RED)
                text = "Seleccione una especialidad"
            }
            return
        }
        if(binding.etBirthDate.text.toString().isEmpty() && binding.etBirthDate.text.isBlank()){
            binding.etBirthDate.error = "Campo requerido"
            return
        }

        if(binding.etSfees.text.toString().isEmpty() && binding.etSfees.text.isBlank()){
            binding.etSfees.error = "Campo requerido"
            return
        }
        registerDoctor()
    }

    private fun registerDoctor(){
        showProgressBar()
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
        doctor.specialityId = specialitySelected.specialityId


        CoroutineScope(Dispatchers.IO).launch {
            val call = userUseCase.registerDoctor(doctor)
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