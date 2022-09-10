package com.upc.hasis_app.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.upc.hasis_app.databinding.FragmentLoginBinding

abstract class BaseDialog(context: Context) : Dialog(context) {


    private var activity: Activity? = null



    constructor(context: Context, activity: Activity) : this(context) {
            this.activity = activity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    protected abstract fun initialize()

}