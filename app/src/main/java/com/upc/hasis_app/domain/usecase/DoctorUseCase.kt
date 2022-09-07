package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.request.EditarDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.model.response.LoginDoctorResponse
import com.upc.hasis_app.data.repository.DoctorRepositoryImp
import retrofit2.Response
import javax.inject.Inject

class DoctorUseCase @Inject constructor(
    private val doctorRepositoryImp: DoctorRepositoryImp
) {

//    suspend fun getAllDoctors(): Response<List<CrearDoctorResponse>> {
//        return doctorRepositoryImp.getAllDoctors();
//    }

    suspend fun getDoctorById(id: Int): Response<CrearDoctorResponse> {
        return doctorRepositoryImp.getDoctorById(id);
    }

    suspend fun loginDoctor(loginRequest: LoginRequest): Response<LoginDoctorResponse> {
        return doctorRepositoryImp.loginDoctor(loginRequest);
    }

    suspend fun createDoctor(crearDoctorRequest: CrearDoctorRequest): Response<CrearDoctorResponse> {
        return doctorRepositoryImp.createDoctor(crearDoctorRequest)
    }

    suspend fun updateDoctor(id:Int, editarDoctorRequest: EditarDoctorRequest): Response<CrearDoctorResponse> {
        return doctorRepositoryImp.updateDoctor(id, editarDoctorRequest)
    }

    suspend fun deleteDoctorById(id: Int): Response<Void> {
        return doctorRepositoryImp.deleteDoctorById(id);
    }

}