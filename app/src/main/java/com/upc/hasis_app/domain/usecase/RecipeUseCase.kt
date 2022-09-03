package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.CrearDoctorRequest
import com.upc.hasis_app.data.model.request.CrearRecipeRequest
import com.upc.hasis_app.data.model.response.CrearDoctorResponse
import com.upc.hasis_app.data.model.response.CrearRecipeResponse
import com.upc.hasis_app.data.repository.PatientRepositoryImp
import com.upc.hasis_app.data.repository.RecipeRepositoryImp
import retrofit2.Response
import javax.inject.Inject

class RecipeUseCase @Inject constructor(
    private val recipeRepositoryImp: RecipeRepositoryImp
)
{
    suspend fun gerRecipeById(id: Int): Response<CrearRecipeResponse> {
        return recipeRepositoryImp.getRecipeById(id);
    }

    suspend fun getAllRecipesByPatient(patientId: Int): Response<List<CrearRecipeResponse>> {
        return recipeRepositoryImp.getAllRecipesByPatient(patientId);
    }

    suspend fun createRecipe(patientId: Int ,crearRecipeRequest: CrearRecipeRequest): Response<CrearRecipeResponse> {
        return recipeRepositoryImp.createRecipe(patientId, crearRecipeRequest)
    }

    suspend fun updateRecipe(id:Int, crearRecipeRequest: CrearRecipeRequest): Response<CrearRecipeResponse> {
        return recipeRepositoryImp.updateRecipe(id, crearRecipeRequest)
    }

    suspend fun deleteDoctorById(id: Int): Response<Void> {
        return recipeRepositoryImp.deleteRecipeById(id);
    }
}