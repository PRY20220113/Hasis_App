package com.upc.hasis_app.data.api

import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.request.CrearPacienteRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.model.response.CrearPacienteResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiRest {


    @GET("doctor")
    suspend fun getAllDoctors(): Response<List<CrearDoctorResponse>>

    @GET("doctor/{doctor}")
    suspend fun getDoctorById(@Path("doctor") id: Int): Response<CrearDoctorResponse>

    @POST("doctor")
    suspend fun createDoctor(@Body crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse>

    @PUT("doctor/{doctor}")
    suspend fun updateDoctor(@Path("doctor") id: Int, @Body crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse>

    @DELETE("doctor/{doctor}")
    suspend fun deleteDoctorById(@Path("doctor") id: Int ): Response<Void>



    @GET("doctor/{doctor}/patient")
    suspend fun getPatientsbyDoctor(@Path("doctor") id: Int): Response<List<CrearPacienteResponse>>

    @POST("doctor/{doctor}/patient")
    suspend fun createPatient(@Path("doctor") id: Int,@Body crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse>

    @PUT("patient/{patient}")
    suspend fun updatePatient(@Path("patient") id: Int,@Body crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse>

    @DELETE("patient/{patient}")
    suspend fun deletePatientById(@Path("patient") id: Int ): Response<Void>


}