package com.upc.hasis_app.domain.repository

import com.upc.hasis_app.data.model.request.CreateRecipeRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Recipe
import retrofit2.Response

interface RecipeRepository {

    suspend fun getRecipeById(medicineId: Int): Response<ResponseDTO<Recipe>>

    suspend fun createRecipe(createRecipeRequest: CreateRecipeRequest): Response<ResponseDTO<Recipe>>

    suspend fun getActiveRecipesOfPatient(patientId: Int): Response<ResponseDTO<List<Recipe>>>


}