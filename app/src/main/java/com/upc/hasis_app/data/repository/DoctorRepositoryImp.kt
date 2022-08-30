package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.domain.repository.DoctorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class DoctorRepositoryImp @Inject constructor(
   private val apiRest: ApiRest,
) : DoctorRepository {

    override suspend fun getAllDoctors(): Response<List<CrearDoctorResponse>> {
        return withContext(Dispatchers.IO) {
             apiRest.getAllDoctors()
        }
    }

    override suspend fun getDoctorById(id:Int): Response<CrearDoctorResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.getDoctorById(id)
        }
    }

    override suspend fun createDoctor(crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.createDoctor(crearDoctorRequest)
        }
    }

    override suspend fun updateDoctor(id: Int, crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.updateDoctor(id, crearDoctorRequest)
        }
    }

    override suspend fun deleteDoctorById(id: Int): Response<Void> {
        return withContext(Dispatchers.IO) {
            apiRest.deleteDoctorById(id)
        }
    }

}