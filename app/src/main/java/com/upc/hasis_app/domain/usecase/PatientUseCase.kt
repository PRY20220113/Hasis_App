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

    suspend fun getPatientsByDoctor(id:Int): Response<List<CrearPacienteResponse>> {
        return patientRepositoryImp.getPatientsByDoctor(id)
    }


    suspend fun createPatient(id:Int, crearDoctorRequest: CrearPacienteRequest): Response<CrearPacienteResponse> {
        return patientRepositoryImp.createPatient(id, crearDoctorRequest)
    }

    suspend fun updatePatient(id:Int, crearDoctorRequest: CrearPacienteRequest): Response<CrearPacienteResponse> {
        return patientRepositoryImp.updatePatient(id, crearDoctorRequest)
    }

    suspend fun deletePatientById(id: Int): Response<Void> {
        return patientRepositoryImp.deletePatientById(id);
    }
}