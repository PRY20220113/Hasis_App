package com.upc.hasis_app.domain.repository

import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.request.CrearPacienteRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.model.response.CrearPacienteResponse
import retrofit2.Response

interface PatientRepository {

    suspend fun getPatientsByDoctor(id:Int): Response<List<CrearPacienteResponse>>

    suspend fun createPatient(id:Int, crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse>

    suspend fun updatePatient(id: Int,crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse>

    suspend fun deletePatientById(id: Int): Response<Void>

}
