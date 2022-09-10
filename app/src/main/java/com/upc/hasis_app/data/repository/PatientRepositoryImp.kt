package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.request.CrearPacienteRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.CrearPacienteResponse
import com.upc.hasis_app.data.model.response.LoginDoctorResponse
import com.upc.hasis_app.data.model.response.LoginPatientResponse
import com.upc.hasis_app.domain.repository.PatientRepository
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PatientRepositoryImp @Inject constructor(
    private val apiRest: ApiRest,
    private val preferencesUseCase: PreferencesUseCase

) : PatientRepository {


    override suspend fun getPatientById(id:Int): Response<CrearPacienteResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.getPatientById(id, preferencesUseCase.getToken()!!)
        }
    }

    override suspend fun createPatient(crearPacienteRequest: CrearPacienteRequest): Response<CrearPacienteResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.createPatient(crearPacienteRequest)
        }
    }

    override suspend fun updatePatient(
        id: Int,
        crearPacienteRequest: CrearPacienteRequest
    ): Response<CrearPacienteResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.updatePatient(id, crearPacienteRequest, preferencesUseCase.getToken()!!)
        }
    }

    override suspend fun deletePatientById(id: Int): Response<Void> {
        return withContext(Dispatchers.IO) {
            apiRest.deletePatientById(id, preferencesUseCase.getToken()!!)
        }
    }

    override suspend fun loginPatient(loginRequest: LoginRequest): Response<LoginPatientResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.loginPatient(loginRequest)
        }
    }
}