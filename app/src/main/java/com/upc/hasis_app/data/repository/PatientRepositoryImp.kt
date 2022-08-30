package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.request.CrearPacienteRequest
import com.upc.hasis_app.data.model.response.CrearPacienteResponse
import com.upc.hasis_app.domain.repository.PatientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PatientRepositoryImp @Inject constructor(
    private val apiRest: ApiRest,
) : PatientRepository {


    override suspend fun getPatientsByDoctor(id:Int): Response<List<CrearPacienteResponse>> {
        return withContext(Dispatchers.IO) {
            apiRest.getPatientsbyDoctor(id)
        }
    }

    override suspend fun createPatient(id:Int,crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.createPatient(id, crearPacienteRequest)
        }
    }

    override suspend fun updatePatient(
        id: Int,
        crearPacienteRequest: CrearPacienteRequest
    ): Response<CrearPacienteResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.updatePatient(id, crearPacienteRequest)
        }
    }

    override suspend fun deletePatientById(id: Int): Response<Void> {
        return withContext(Dispatchers.IO) {
            apiRest.deletePatientById(id)
        }
    }
}