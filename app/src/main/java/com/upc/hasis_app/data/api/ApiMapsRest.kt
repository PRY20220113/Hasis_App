package com.upc.hasis_app.data.api

import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiMapsRest {


    @GET("maps/api/place/nearbysearch/json")
    suspend fun getNearbyPlaces(@Query("keyword")     keyword:String,
                                @Query("location")   location:String,
                                @Query("radius")     radius:String,
                                @Query("key")        key:String): Response<Any>
}