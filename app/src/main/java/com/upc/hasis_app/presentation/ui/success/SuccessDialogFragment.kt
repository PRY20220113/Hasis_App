package com.upc.hasis_app.presentation.ui.success

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.upc.hasis_app.R
import com.upc.hasis_app.databinding.FragmentErrorDialogBinding
import com.upc.hasis_app.databinding.FragmentLoginBinding
import com.upc.hasis_app.databinding.FragmentSuccessDialogBinding

class SuccessDialogFragment : Fragment() {

    private lateinit var binding: FragmentSuccessDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

}