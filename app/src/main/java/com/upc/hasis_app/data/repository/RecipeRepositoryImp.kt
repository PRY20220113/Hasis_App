package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.request.CrearRecipeRequest
import com.upc.hasis_app.data.model.response.CrearRecipeResponse
import com.upc.hasis_app.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RecipeRepositoryImp @Inject constructor(
    private val apiRest: ApiRest,
) : RecipeRepository {

    override suspend fun getRecipeById(id: Int): Response<CrearRecipeResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.getRecipeById(id)
        }
    }

    override suspend fun getAllRecipesByPatient(id: Int): Response<List<CrearRecipeResponse>> {
        return withContext(Dispatchers.IO) {
            apiRest.getAllRecipesByPatient(id)
        }
    }

    override suspend fun createRecipe(
        id: Int,
        crearRecipeRequest: CrearRecipeRequest
    ): Response<CrearRecipeResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.createRecipe(id, crearRecipeRequest)
        }
    }

    override suspend fun updateRecipe(
        id: Int,
        crearRecipeRequest: CrearRecipeRequest
    ): Response<CrearRecipeResponse> {
        return withContext(Dispatchers.IO) {
            apiRest.updateRecipe(id,crearRecipeRequest)
        }
    }

    override suspend fun deleteRecipeById(id: Int): Response<Void> {
        return withContext(Dispatchers.IO) {
            apiRest.deleteDoctorById(id)
        }
    }
}