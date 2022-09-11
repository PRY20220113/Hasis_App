package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.request.CreateRecipeRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.request.RegisterDoctorRequest
import com.upc.hasis_app.data.model.request.RegisterPatientRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Patient
import com.upc.hasis_app.domain.repository.RecipeRepository
import com.upc.hasis_app.domain.repository.UserRepository
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val apiRest: ApiRest,
    private val preferencesUseCase: PreferencesUseCase
) : UserRepository {


    override suspend fun login(loginRequest: LoginRequest): Response<ResponseDTO<Any>> {
        return apiRest.login(loginRequest)
    }

    override suspend fun registerDoctor(registerDoctorRequest: RegisterDoctorRequest): Response<ResponseDTO<Doctor>> {
        return apiRest.registerDoctor(registerDoctorRequest)
    }

    override suspend fun registerPatient(registerPatientRequest: RegisterPatientRequest): Response<ResponseDTO<Patient>> {
        return apiRest.registerPatient(registerPatientRequest)
    }


}