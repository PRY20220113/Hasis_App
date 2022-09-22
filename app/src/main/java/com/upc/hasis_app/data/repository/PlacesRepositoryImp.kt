package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiMapsRest
import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Speciality

import com.upc.hasis_app.domain.repository.DoctorRepository
import com.upc.hasis_app.domain.repository.PlacesRepository
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PlacesRepositoryImp @Inject constructor(
   private val apiMapsRest: ApiMapsRest,
   private val preferencesUseCase: PreferencesUseCase
) : PlacesRepository {

   private val GOOGLE_API_KEY = "AIzaSyCaUSKnE9wkpEb3g-UGkDrAWovR7jB7byU"

   override suspend fun getNearbyPharmacies(location:String): Response<Any> {
     return apiMapsRest.getNearbyPlaces("farmacia",location, "100", GOOGLE_API_KEY)
   }

}



