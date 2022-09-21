package com.upc.hasis_app.data.api

import com.upc.hasis_app.data.model.request.*
import com.upc.hasis_app.data.model.response.*
import com.upc.hasis_app.domain.entity.*
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface ApiRest {


    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseDTO<Any>>

    @POST("user/registerDoctor")
    suspend fun registerDoctor(@Body registerDoctorRequest: RegisterDoctorRequest): Response<ResponseDTO<Doctor>>

    @POST("user/registerPatient")
    suspend fun registerPatient(@Body registerPatientRequest: RegisterPatientRequest): Response<ResponseDTO<Patient>>


    @GET("/doctor")
    suspend fun getDoctorById(@Query("doctorId") doctorId: Int, @Header("Authorization") token: String): Response<ResponseDTO<Doctor>>

    @GET("/patient")
    suspend fun getPatientById(@Query("patientId") patientId: Int, @Header("Authorization") token: String): Response<ResponseDTO<Patient>>




    @GET("recipe")
    suspend fun getRecipeById(@Query("recipeId") recipeId: Int, @Header("Authorization") token: String): Response<ResponseDTO<Recipe>>

    @POST("recipe")
    suspend fun createRecipe(@Body createRecipeRequest: CreateRecipeRequest, @Header("Authorization") token: String): Response<ResponseDTO<Recipe>>

    @GET("recipe/patient")
    suspend fun getActiveRecipesOfPatient(@Query("patientId") patientId: Int, @Header("Authorization") token: String): Response<ResponseDTO<List<Recipe>>>

    @GET("recipe/patient/speciality")
    suspend fun getActiveRecipeOfPatientBySpeciality(@Query("patientId") patientId: Int,@Query("specialityId") specialityId: Int, @Header("Authorization") token: String): Response<ResponseDTO<Recipe>>

    @GET("recipe/patient/speciality/active")
    suspend fun getActiveSpecialityOfPatient(@Query("patientId") patientId: Int, @Header("Authorization") token: String): Response<ResponseDTO<List<Speciality>>>



    @GET("medicine")
    suspend fun getMedicineById(@Query("medicineId") medicineId: Int, @Header("Authorization") token: String): Response<ResponseDTO<Medicine>>

    @PUT("medicine")
    suspend fun updateMedicine(@Query("medicineId") medicineId: Int,@Body updateMedicineRequest: UpdateMedicineRequest, @Header("Authorization") token: String): Response<ResponseDTO<Medicine>>


    @GET("speciality/all")
    suspend fun getSpecialities(): Response<ResponseDTO<List<Speciality>>>


}