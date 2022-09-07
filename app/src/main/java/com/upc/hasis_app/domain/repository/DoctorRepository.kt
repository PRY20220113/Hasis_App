package com.upc.hasis_app.domain.repository

import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.request.EditarDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.model.response.LoginDoctorResponse
import retrofit2.Response

interface DoctorRepository {

    //suspend fun getAllDoctors(): Response<List<CrearDoctorResponse>>

    suspend fun loginDoctor(loginRequest: LoginRequest): Response<LoginDoctorResponse>

    suspend fun getDoctorById(id: Int): Response<CrearDoctorResponse>

    suspend fun createDoctor(crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse>

    suspend fun updateDoctor(id: Int, editarDoctorRequest: EditarDoctorRequest): Response<CrearDoctorResponse>

    suspend fun deleteDoctorById(id: Int): Response<Void>
}