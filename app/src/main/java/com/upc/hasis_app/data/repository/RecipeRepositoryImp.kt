package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.request.CreateRecipeRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Recipe
import com.upc.hasis_app.domain.repository.RecipeRepository
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RecipeRepositoryImp @Inject constructor(
    private val apiRest: ApiRest,
    private val preferencesUseCase: PreferencesUseCase
) : RecipeRepository {

    override suspend fun getRecipeById(medicineId: Int): Response<ResponseDTO<Recipe>> {
        return apiRest.getRecipeById(medicineId, preferencesUseCase.getToken()!!)
    }

    override suspend fun createRecipe(createRecipeRequest: CreateRecipeRequest): Response<ResponseDTO<Recipe>> {
        return apiRest.createRecipe(createRecipeRequest, preferencesUseCase.getToken()!!)
    }

    override suspend fun getActiveRecipesOfPatient(patientId: Int): Response<ResponseDTO<List<Recipe>>> {
        return apiRest.getActiveRecipesOfPatient(patientId, preferencesUseCase.getToken()!!)
    }

}