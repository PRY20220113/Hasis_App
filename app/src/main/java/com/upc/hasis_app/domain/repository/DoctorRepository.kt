package com.upc.hasis_app.domain.repository


import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Doctor
import retrofit2.Response

interface DoctorRepository {

    suspend fun getDoctorById(doctorId: Int): Response<ResponseDTO<Doctor>>

}