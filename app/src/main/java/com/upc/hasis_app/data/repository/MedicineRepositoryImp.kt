package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.request.CreateRecipeRequest
import com.upc.hasis_app.data.model.request.UpdateMedicineRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.domain.repository.MedicineRepository
import com.upc.hasis_app.domain.repository.RecipeRepository
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MedicineRepositoryImp @Inject constructor(
    private val apiRest: ApiRest,
    private val preferencesUseCase: PreferencesUseCase
) : MedicineRepository {

    override suspend fun getMedicineById(medicineId: Int): Response<ResponseDTO<Medicine>> {
        return apiRest.getMedicineById(medicineId, preferencesUseCase.getToken()!!)
    }

    override suspend fun updateMedicine(
        medicineId: Int,
        updateMedicineRequest: UpdateMedicineRequest
    ): Response<ResponseDTO<Medicine>> {
        return apiRest.updateMedicine(medicineId,updateMedicineRequest, preferencesUseCase.getToken()!!)
    }

}