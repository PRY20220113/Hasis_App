package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.CreateRecipeRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.data.repository.RecipeRepositoryImp
import com.upc.hasis_app.domain.entity.Patient
import com.upc.hasis_app.domain.entity.Recipe
import retrofit2.Response
import javax.inject.Inject

class RecipeUseCase @Inject constructor(
    private val recipeRepositoryImp: RecipeRepositoryImp
)
{

    suspend fun getRecipeById(recipeId: Int) : Response<ResponseDTO<Recipe>> {
        return recipeRepositoryImp.getRecipeById(recipeId)
    }

    suspend fun createRecipe(createRecipeRequest: CreateRecipeRequest) : Response<ResponseDTO<Recipe>> {
        return recipeRepositoryImp.createRecipe(createRecipeRequest)
    }

    suspend fun getActiveRecipeOfPatient(patientId: Int) : Response<ResponseDTO<Recipe>> {
        return recipeRepositoryImp.getActiveRecipeOfPatient(patientId)
    }
}