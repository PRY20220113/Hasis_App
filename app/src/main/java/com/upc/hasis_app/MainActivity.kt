package com.upc.hasis_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.upc.hasis_app.data.apí.ApiRest
import com.upc.hasis_app.data.model.request.ObtenerFactosRequest
import com.upc.hasis_app.data.model.response.ObtenerFactosResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MainActivity : AppCompatActivity() {

    val API_URL = "https://catfact.ninja/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun obtenerFactos() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiRest::class.java).contributors().execute()
            val response = call.body() as ObtenerFactosResponse?
            runOnUiThread {
                Log.i("Response", response.toString())
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        obtenerFactos()

    }

}