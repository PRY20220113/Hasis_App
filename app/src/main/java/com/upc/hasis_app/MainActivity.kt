package com.upc.hasis_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.databinding.ActivityMainBinding
import com.upc.hasis_app.domain.usecase.DoctorUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var doctorUseCase: DoctorUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        var request =  CrearDoctorRequest("Bryan", "Miramira", "bbqms@hotmail.com","71811376", "123456789", "950040337", "bryanxd1234")

        GlobalScope.launch(Dispatchers.Main) {
            Log.i("test", doctorUseCase.createDoctor(request).body().toString());
            Log.i("test", doctorUseCase.getAllDoctors().body().toString());
            Log.i("test", doctorUseCase.getDoctorById(2).body().toString());
            Log.i("test", doctorUseCase.updateDoctor(2, request).body().toString());
        }

    }

}