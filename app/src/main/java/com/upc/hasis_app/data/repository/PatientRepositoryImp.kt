package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Patient

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
    override suspend fun getPatientById(patientId: Int): Response<ResponseDTO<Patient>> {
        return apiRest.getPatientById(patientId, preferencesUseCase.getToken()!!)
    }


}