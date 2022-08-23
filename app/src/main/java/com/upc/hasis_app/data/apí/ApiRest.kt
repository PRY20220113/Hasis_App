package com.upc.hasis_app.data.apí

import com.upc.hasis_app.data.model.request.ObtenerFactosRequest
import com.upc.hasis_app.data.model.response.ObtenerFactosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRest {

    @GET("fact")
    fun contributors(): Call<ObtenerFactosResponse>

}