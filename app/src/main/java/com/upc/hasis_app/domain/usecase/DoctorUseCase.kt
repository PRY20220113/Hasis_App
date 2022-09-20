package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.RegisterDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.data.repository.DoctorRepositoryImp
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Speciality
import retrofit2.Response
import javax.inject.Inject

class DoctorUseCase @Inject constructor(
    private val doctorRepositoryImp: DoctorRepositoryImp
) {

    suspend fun getDoctorById(doctorId: Int) : Response<ResponseDTO<Doctor>> {
        return doctorRepositoryImp.getDoctorById(doctorId)
    }

    suspend fun getSpecialities() : Response<ResponseDTO<List<Speciality>>> {
        return doctorRepositoryImp.getSpecialities()
    }

}