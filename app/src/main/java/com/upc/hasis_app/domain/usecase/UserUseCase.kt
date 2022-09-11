package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.RegisterDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.request.RegisterPatientRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.data.repository.UserRepositoryImp
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient
import com.upc.hasis_app.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepositoryImp: UserRepositoryImp
) {

    suspend fun login(loginRequest: LoginRequest): Response<ResponseDTO<Any>> {
        return userRepositoryImp.login(loginRequest)
    }

    suspend fun registerDoctor(registerDoctorRequest: RegisterDoctorRequest): Response<ResponseDTO<Doctor>> {
        return userRepositoryImp.registerDoctor(registerDoctorRequest);
    }

    suspend fun registerPatient(registerPatientRequest: RegisterPatientRequest): Response<ResponseDTO<Patient>> {
        return userRepositoryImp.registerPatient(registerPatientRequest);
    }


}