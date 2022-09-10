package com.upc.hasis_app.data.api

import com.upc.hasis_app.data.model.request.*
import com.upc.hasis_app.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface ApiRest {

    @GET("doctors/{doctorId}")
    suspend fun getDoctorById(@Path("doctorId") doctorId: Int, @Header("Authorization") token: String): Response<CrearDoctorResponse>

    @PUT("doctors/{doctorId}")
    suspend fun updateDoctor(@Path("doctorId") id: Int, @Body editarDoctorRequest: EditarDoctorRequest, @Header("Authorization") token: String): Response<CrearDoctorResponse>

    @DELETE("doctors/{doctorId}")
    suspend fun deleteDoctorById(@Path("doctorId") id: Int, @Header("Authorization") token: String ): Response<Void>

    @POST("doctors/auth/sign-up")
    suspend fun createDoctor(@Body crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse>

    @POST("doctors/auth/sign-in")
    suspend fun loginDoctor(@Body loginRequest: LoginRequest): Response<LoginDoctorResponse>






    @GET("patients/{patientId}")
    suspend fun getPatientById(@Path("patientId") patientId: Int, @Header("Authorization") token: String): Response<CrearPacienteResponse>

    @PUT("patient/{patient}")
    suspend fun updatePatient(@Path("patient") id: Int,@Body crearPacienteRequest: CrearPacienteRequest, @Header("Authorization") token: String): Response<CrearPacienteResponse>

    @DELETE("patient/{patient}")
    suspend fun deletePatientById(@Path("patient") id: Int, @Header("Authorization") token: String): Response<Void>

    @POST("patients/auth/sign-up")
    suspend fun createPatient(@Body crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse>

    @POST("doctors/auth/sign-in")
    suspend fun loginPatient(@Body loginRequest: LoginRequest): Response<LoginPatientResponse>



    @GET("recipe/{recipeId}")
    suspend fun getRecipeById(@Path("recipeId") id: Int, @Header("Authorization") token: String): Response<CrearRecipeResponse>

    @GET("patient/{patientId}/recipe")
    suspend fun getAllRecipesByPatient(@Path("patientId") id: Int, @Header("Authorization") token: String): Response<List<CrearRecipeResponse>>

    @POST("patient/{patientId}/recipe")
    suspend fun createRecipe(@Path("patientId") patientId: Int, @Body crearPacienteRequest: CrearRecipeRequest, @Header("Authorization") token: String): Response<CrearRecipeResponse>

    @PUT("recipe/{recipeId}")
    suspend fun updateRecipe(@Path("recipeId") recipeId: Int, @Body crearPacienteRequest: CrearRecipeRequest, @Header("Authorization") token: String): Response<CrearRecipeResponse>

    @DELETE("recipe/{recipeId}")
    suspend fun deleteRecipeById(@Path("recipeId") id: Int , @Header("Authorization") token: String): Response<Void>

}