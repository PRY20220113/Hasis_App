package com.upc.hasis_app.data.api

import com.upc.hasis_app.data.model.request.*
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.model.response.CrearPacienteResponse
import com.upc.hasis_app.data.model.response.CrearRecipeResponse
import com.upc.hasis_app.data.model.response.LoginDoctorResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiRest {

    @GET("doctors/{doctorId}")
    suspend fun getDoctorById(@Path("doctorId") doctorId: Int): Response<CrearDoctorResponse>

    @PUT("doctors/{doctorId}")
    suspend fun updateDoctor(@Path("doctorId") id: Int, @Body editarDoctorRequest: EditarDoctorRequest): Response<CrearDoctorResponse>

    @DELETE("doctors/{doctorId}")
    suspend fun deleteDoctorById(@Path("doctorId") id: Int ): Response<Void>

    @POST("doctors/auth/sign-up")
    suspend fun createDoctor(@Body crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse>

    @POST("doctors/auth/sign-in")
    suspend fun loginDoctor(@Body loginRequest: LoginRequest): Response<LoginDoctorResponse>








    @GET("patients/{patientId}")
    suspend fun getPatientById(@Path("patientId") patientId: Int): Response<CrearPacienteResponse>

    @POST("patients/auth/sign-up")
    suspend fun createPatient(@Body crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse>

    @PUT("patient/{patient}")
    suspend fun updatePatient(@Path("patient") id: Int,@Body crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse>

    @DELETE("patient/{patient}")
    suspend fun deletePatientById(@Path("patient") id: Int ): Response<Void>



    @GET("recipe/{recipeId}")
    suspend fun getRecipeById(@Path("recipeId") id: Int): Response<CrearRecipeResponse>

    @GET("patient/{patientId}/recipe")
    suspend fun getAllRecipesByPatient(@Path("patientId") id: Int): Response<List<CrearRecipeResponse>>

    @POST("patient/{patientId}/recipe")
    suspend fun createRecipe(@Path("patientId") patientId: Int, @Body crearPacienteRequest: CrearRecipeRequest): Response<CrearRecipeResponse>

    @PUT("recipe/{recipeId}")
    suspend fun updateRecipe(@Path("recipeId") recipeId: Int, @Body crearPacienteRequest: CrearRecipeRequest): Response<CrearRecipeResponse>

    @DELETE("recipe/{recipeId}")
    suspend fun deleteRecipeById(@Path("recipeId") id: Int ): Response<Void>

}