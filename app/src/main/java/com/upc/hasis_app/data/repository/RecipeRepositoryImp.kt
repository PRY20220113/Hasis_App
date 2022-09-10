package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.request.CrearRecipeRequest
import com.upc.hasis_app.data.model.response.CrearRecipeResponse
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

    override suspend fun getRecipeById(id: Int): Response<CrearRecipeResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.getRecipeById(id,  preferencesUseCase.getToken()!!)
        }
    }

    override suspend fun getAllRecipesByPatient(id: Int): Response<List<CrearRecipeResponse>> {
        return withContext(Dispatchers.IO) {
            apiRest.getAllRecipesByPatient(id,  preferencesUseCase.getToken()!!)
        }
    }

    override suspend fun createRecipe(
        id: Int,
        crearRecipeRequest: CrearRecipeRequest
    ): Response<CrearRecipeResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.createRecipe(id, crearRecipeRequest,  preferencesUseCase.getToken()!!)
        }
    }

    override suspend fun updateRecipe(
        id: Int,
        crearRecipeRequest: CrearRecipeRequest
    ): Response<CrearRecipeResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.updateRecipe(id,crearRecipeRequest,  preferencesUseCase.getToken()!!)
        }
    }

    override suspend fun deleteRecipeById(id: Int): Response<Void> {
        return withContext(Dispatchers.IO) {
            apiRest.deleteDoctorById(id,  preferencesUseCase.getToken()!!)
        }
    }
}