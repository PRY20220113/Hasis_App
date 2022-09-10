package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.request.EditarDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.model.response.LoginDoctorResponse
import com.upc.hasis_app.domain.repository.DoctorRepository
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class DoctorRepositoryImp @Inject constructor(
   private val apiRest: ApiRest,
   private val preferencesUseCase: PreferencesUseCase
) : DoctorRepository {

//    override suspend fun getAllDoctors(): Response<List<CrearDoctorResponse>> {
//        return withContext(Dispatchers.IO) {
//             apiRest.getAllDoctors()
//        }
//    }

    override suspend fun getDoctorById(id:Int): Response<CrearDoctorResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.getDoctorById(id, preferencesUseCase.getToken()!!)
        }
    }

    override suspend fun loginDoctor(loginRequest: LoginRequest): Response<LoginDoctorResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.loginDoctor(loginRequest)
        }
    }


    override suspend fun createDoctor(crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.createDoctor(crearDoctorRequest)
        }
    }

    override suspend fun updateDoctor(id: Int, editarDoctorRequest: EditarDoctorRequest ): Response<CrearDoctorResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.updateDoctor(id, editarDoctorRequest,  preferencesUseCase.getToken()!!)
        }
    }

    override suspend fun deleteDoctorById(id: Int): Response<Void> {
        return withContext(Dispatchers.IO) {
            apiRest.deleteDoctorById(id,  preferencesUseCase.getToken()!!)
        }
    }

}