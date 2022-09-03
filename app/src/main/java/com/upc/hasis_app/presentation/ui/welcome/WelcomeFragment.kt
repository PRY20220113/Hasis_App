package com.upc.hasis_app.presentation.ui.welcome

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.upc.hasis_app.MainActivity
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentWelcomeBinding
import com.upc.hasis_app.presentation.view_model.WelcomeStatus
import com.upc.hasis_app.presentation.view_model.WelcomeViewModel
import com.upc.hasis_app.util.tts.TTSHelper
import kotlinx.coroutines.delay
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {


    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel: WelcomeViewModel by activityViewModels()
    private lateinit var ttsHelper: TTSHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        ttsHelper = (activity as MainActivity).ttsHelper
        initObservers()
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.go_to_login)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.go_to_register)
        }

    }

    private fun initObservers(){
        viewModel.currentState.observe(viewLifecycleOwner) {
            when (it) {
                is WelcomeStatus.Success -> {
                    speakWelcomeUser()
                }
            }
        }
    }

    private fun speakWelcomeUser() {
        ttsHelper.speakOut("Bienvenido a jasis; tu asistente de gestión de medicamentos. Para inicar sesión selecciona por favor la opción sobre la parte inferior derecha o para " +
                "registrarte selecciona la opción de la parte inferior izquierda")
    }

}