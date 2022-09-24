package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.RegisterDoctorRequest
import com.upc.hasis_app.data.model.request.LoginRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.data.repository.DoctorRepositoryImp
import com.upc.hasis_app.data.repository.PlacesRepositoryImp
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Speciality
import retrofit2.Response
import javax.inject.Inject

class PlacesUseCase @Inject constructor(
    private val placesRepositoryImp: PlacesRepositoryImp
) {
    suspend fun getNerbyPharmacies(location: String) : Response<Any> {
        return placesRepositoryImp.getNearbyPharmacies(location);
    }

}