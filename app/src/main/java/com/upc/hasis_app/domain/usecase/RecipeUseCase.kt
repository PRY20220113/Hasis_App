package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.CreateRecipeRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.data.repository.RecipeRepositoryImp
import com.upc.hasis_app.domain.entity.Recipe
import com.upc.hasis_app.domain.entity.Speciality
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

    suspend fun getActiveRecipesOfPatient(patientId: Int) : Response<ResponseDTO<List<Recipe>>> {
        return recipeRepositoryImp.getActiveRecipesOfPatient(patientId)
    }
    suspend fun getActiveRecipesBySpecialityOfPatient(patientId: Int, specialityId: Int) : Response<ResponseDTO<Recipe>> {
        return recipeRepositoryImp.getActiveRecipesBySpecialityOfPatient(patientId, specialityId)
    }

    suspend fun getActiveSpecialityOfPatient(patientId: Int) : Response<ResponseDTO<List<Speciality>>> {
        return recipeRepositoryImp.getActiveSpecialityOfPatient(patientId)
    }
}