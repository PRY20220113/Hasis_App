package com.upc.hasis_app.domain.repository


import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Speciality
import retrofit2.Response

interface PlacesRepository {

    suspend fun getNearbyPharmacies(location: String): Response<Any>

}