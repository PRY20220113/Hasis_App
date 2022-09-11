package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Doctor

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

   override suspend fun getDoctorById(doctorId: Int): Response<ResponseDTO<Doctor>> {
      return apiRest.getDoctorById(doctorId, preferencesUseCase.getToken()!!)
   }

}



