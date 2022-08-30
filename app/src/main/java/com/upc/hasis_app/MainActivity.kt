package com.upc.hasis_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.response.ObtenerFactosResponse
import com.upc.hasis_app.databinding.ActivityMainBinding
import com.upc.hasis_app.domain.usecase.ObtenerFactosUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.multibindings.IntKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var obtenerFactosUseCase: ObtenerFactosUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        GlobalScope.launch(Dispatchers.Main) {
            Log.i("test", obtenerFactosUseCase.obtenerFactosUseCase().toString());
        }

    }

}