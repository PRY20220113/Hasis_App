package com.upc.hasis_app.data.api

import com.upc.hasis_app.data.model.response.ObtenerFactosResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiRest {

    @GET("fact")
    fun contributors(): Call<ObtenerFactosResponse>

}