package com.upc.hasis_app.presentation.ui.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.MainActivity
import com.upc.hasis_app.R
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.databinding.FragmentLoginBinding
import com.upc.hasis_app.domain.usecase.DoctorUseCase
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.presentation.view_model.LoginViewModel
import com.upc.hasis_app.presentation.view_model.ResultStatus
import com.upc.hasis_app.presentation.view_model.UserActionStatus
import com.upc.hasis_app.util.ErrorDialog
import com.upc.hasis_app.util.stt.STTHelper
import com.upc.hasis_app.util.tts.TTSHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var sttHelper : STTHelper
    private lateinit var ttsHelper: TTSHelper
    private lateinit var binding: FragmentLoginBinding
    private val viewModel : LoginViewModel by activityViewModels()

    @Inject
    lateinit var doctorUseCase: DoctorUseCase

    @Inject
    lateinit var preferencesUseCase: PreferencesUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        ttsHelper = (activity as MainActivity).ttsHelper
        viewModel.setState(ResultStatus.Success)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sttHelper = STTHelper(context)

        binding.btnLogin.setOnClickListener {
            //findNavController().navigate(R.id.login_doctor_complete)
            doLogin()
        }

        GlobalScope.launch(Dispatchers.IO){
            val loginRequest =  preferencesUseCase.getLoginRequest();
            if(loginRequest != null) {
                binding.tiUsername.setText(loginRequest!!.dni)
                binding.tiPassword.setText(loginRequest.password)
            }
        }


        initObservers()
        initTTSObservers()
        setSpeechRecognizerListeners()
    }

    private fun doLogin(){
        val loginRequest  = LoginRequest()
        loginRequest.dni = binding.tiUsername.text.toString()
        loginRequest.password = binding.tiPassword.text.toString()

//        GlobalScope.launch(Dispatchers.Main) {
//            val response = doctorUseCase.loginDoctor(loginRequest)
//            Log.i("LoginResponse", response.toString())
//            if(response.code() == 200){
//                Log.i("LoginResponse", response.body().toString())
//                preferencesUseCase.setLoginRequest(loginRequest)
//                preferencesUseCase.setToken("Bearer ${response.body()!!.token}")
//
//                viewModel.setState(ResultStatus.LoggedIn)
//
//            } else {
//                val errorResponse = response.errorBody()!!.string()
//                Log.i("LoginResponse", errorResponse )
//                showErrorDialog(errorResponse.toString())
//            }
//        }

        viewModel.setState(ResultStatus.LoggedIn)
    }

    private fun showErrorDialog(message: String){
        val dialog = ErrorDialog(requireContext(), message)
        dialog.requestWindowFeature(1)
        //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun initTTSObservers(){
        ttsHelper.currentState.observe(viewLifecycleOwner) {
            when (it) {
                is ResultStatus.Speaking -> {

                }
                is ResultStatus.SpeakComplete -> {
                    sttHelper.listen()
                }
            }
        }
    }

    private fun initObservers(){
        viewModel.currentState.observe(viewLifecycleOwner) {
            when (it) {
                is ResultStatus.Success -> {
                    if(viewModel.userName == null || viewModel.password == null) {
                        if(viewModel.userName == null) { viewModel.interactWithUser(UserActionStatus.GetUser, ttsHelper) }
                        else { viewModel.interactWithUser(UserActionStatus.GetPassword, ttsHelper)}
                    } else {
                        viewModel.setState(ResultStatus.DataComplete)
                    }

                }
                is ResultStatus.DataComplete -> {
                    doLogin()
                    //findNavController().navigate(R.id.login_doctor_complete)
                }
                is ResultStatus.LoggedIn -> {
                    sttHelper.stopListen()
                    findNavController().navigate(R.id.login_doctor_complete)
                }
                else -> {}
            }
        }
    }

    private val checkValueVal : () -> Unit = {
        viewModel.setState(ResultStatus.Listening)
        if(viewModel.userName == null) { binding.tiUsername.hint = "Escuchando"}
        else { binding.tiPassword.hint = "Escuchando"}
    }

    private val updateValueListened : (ArrayList<String>) -> Unit = {
        if(viewModel.userName == null || viewModel.password == null) {
            if(viewModel.userName == null) {
                binding.tiUsername.setText(it[0].replace("\\s".toRegex(), ""))
                viewModel.userName = it[0]
            } else {
                binding.tiPassword.setText(it[0].replace("\\s".toRegex(), ""))
                viewModel.password = it[0]
            }
            viewModel.setState(ResultStatus.Success)
        } else {
            viewModel.setState(ResultStatus.DataComplete)
        }
    }

    private fun setSpeechRecognizerListeners() {
        sttHelper.setSpeechRecognizerListeners(checkValueVal, updateValueListened)
    }





}