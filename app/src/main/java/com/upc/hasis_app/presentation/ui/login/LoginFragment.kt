package com.upc.hasis_app.presentation.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.MainActivity
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentLoginBinding
import com.upc.hasis_app.presentation.view_model.LoginViewModel
import com.upc.hasis_app.presentation.view_model.ResultStatus
import com.upc.hasis_app.presentation.view_model.UserActionStatus
import com.upc.hasis_app.util.stt.STTHelper
import com.upc.hasis_app.util.tts.TTSHelper

class LoginFragment : Fragment() {

    private lateinit var sttHelper : STTHelper
    private lateinit var ttsHelper: TTSHelper
    private lateinit var binding: FragmentLoginBinding
    private val viewModel : LoginViewModel by activityViewModels()

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
            findNavController().navigate(R.id.login_doctor_complete)
        }

        initObservers()
        initTTSObservers()
        setSpeechRecognizerListeners()
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
                binding.tiUsername.setText(it[0])
                viewModel.userName = it[0]
            } else {
                binding.tiPassword.setText(it[0])
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