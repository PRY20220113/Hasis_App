package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.repository.DoctorRepositoryImp
import retrofit2.Response
import javax.inject.Inject

class DoctorUseCase @Inject constructor(
    private val doctorRepositoryImp: DoctorRepositoryImp
) {

    suspend fun getAllDoctors(): Response<List<CrearDoctorResponse>> {
        return doctorRepositoryImp.getAllDoctors();
    }

    suspend fun getDoctorById(id: Int): Response<CrearDoctorResponse> {
        return doctorRepositoryImp.getDoctorById(id);
    }

    suspend fun createDoctor(crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse> {
        return doctorRepositoryImp.createDoctor(crearDoctorRequest)
    }

    suspend fun updateDoctor(id:Int, crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse> {
        return doctorRepositoryImp.updateDoctor(id, crearDoctorRequest)
    }

    suspend fun deleteDoctorById(id: Int): Response<Void> {
        return doctorRepositoryImp.deleteDoctorById(id);
    }

}