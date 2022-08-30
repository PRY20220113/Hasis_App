package com.upc.hasis_app.domain.repository

import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import retrofit2.Response

interface DoctorRepository {

    suspend fun getAllDoctors(): Response<List<CrearDoctorResponse>>

    suspend fun getDoctorById(id: Int): Response<CrearDoctorResponse>

    suspend fun createDoctor(crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse>

    suspend fun updateDoctor(id: Int, crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse>

    suspend fun deleteDoctorById(id: Int): Response<Void>
}