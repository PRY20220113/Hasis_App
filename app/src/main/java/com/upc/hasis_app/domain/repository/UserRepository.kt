package com.upc.hasis_app.domain.repository

import com.upc.hasis_app.data.model.request.RegisterDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.request.RegisterPatientRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient
import retrofit2.Response

interface UserRepository {


    suspend fun login(loginRequest: LoginRequest): Response<ResponseDTO<Any>>

    suspend fun registerDoctor(registerDoctorRequest: RegisterDoctorRequest): Response<ResponseDTO<Doctor>>

    suspend fun registerPatient(registerPatientRequest: RegisterPatientRequest): Response<ResponseDTO<Patient>>
}