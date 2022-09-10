package com.upc.hasis_app.domain.repository

import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.request.CrearPacienteRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.model.response.CrearPacienteResponse
import com.upc.hasis_app.data.model.response.LoginPatientResponse
import retrofit2.Response

interface PatientRepository {

    suspend fun getPatientById(id:Int): Response<CrearPacienteResponse>

    suspend fun createPatient(crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse>

    suspend fun updatePatient(id: Int,crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse>

    suspend fun deletePatientById(id: Int): Response<Void>

    suspend fun loginPatient(loginRequest: LoginRequest): Response<LoginPatientResponse>
}
