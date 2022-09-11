package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.RegisterPatientRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.data.repository.PatientRepositoryImp
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient
import retrofit2.Response
import javax.inject.Inject

class PatientUseCase @Inject constructor(
    private val patientRepositoryImp: PatientRepositoryImp
)  {

    suspend fun getPatientById(patientId: Int) : Response<ResponseDTO<Patient>> {
        return patientRepositoryImp.getPatientById(patientId)
    }
}