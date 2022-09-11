package com.upc.hasis_app.domain.repository


import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Patient
import retrofit2.Response

interface PatientRepository {

    suspend fun getPatientById(patientId:Int): Response<ResponseDTO<Patient>>

}
