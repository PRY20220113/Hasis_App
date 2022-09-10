package com.upc.hasis_app.util

import android.content.Context
import android.os.Bundle
import com.upc.hasis_app.databinding.FragmentErrorDialogBinding
import com.upc.hasis_app.databinding.FragmentSuccessDialogBinding

class SuccessDialog(context: Context): BaseDialog(context ) {


    private lateinit var message:String

    private lateinit var binding: FragmentSuccessDialogBinding

    constructor(context: Context, message: String) : this(context){
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =   FragmentSuccessDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    override fun initialize() {
        binding.tvSuccessText.text = this.message
        binding.btnBack.setOnClickListener {
            this.dismiss()
        }
    }

}