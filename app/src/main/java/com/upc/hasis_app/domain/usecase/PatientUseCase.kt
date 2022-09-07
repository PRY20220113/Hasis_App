package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.request.CrearPacienteRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.model.response.CrearPacienteResponse
import com.upc.hasis_app.data.repository.DoctorRepositoryImp
import com.upc.hasis_app.data.repository.PatientRepositoryImp
import retrofit2.Response
import javax.inject.Inject

class PatientUseCase @Inject constructor(
    private val patientRepositoryImp: PatientRepositoryImp
)  {

    suspend fun getPatientById(id:Int): Response<CrearPacienteResponse> {
        return patientRepositoryImp.getPatientById(id)
    }

    suspend fun createPatient(crearDoctorRequest: CrearPacienteRequest): Response<CrearPacienteResponse> {
        return patientRepositoryImp.createPatient( crearDoctorRequest)
    }

    suspend fun updatePatient(id:Int, crearDoctorRequest: CrearPacienteRequest): Response<CrearPacienteResponse> {
        return patientRepositoryImp.updatePatient(id, crearDoctorRequest)
    }

    suspend fun deletePatientById(id: Int): Response<Void> {
        return patientRepositoryImp.deletePatientById(id);
    }
}