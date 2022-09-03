package com.upc.hasis_app.domain.repository

import com.upc.hasis_app.data.model.request.CrearPacienteRequest
import com.upc.hasis_app.data.model.request.CrearRecipeRequest
import com.upc.hasis_app.data.model.response.CrearPacienteResponse
import com.upc.hasis_app.data.model.response.CrearRecipeResponse
import retrofit2.Response

interface RecipeRepository {


    suspend fun getRecipeById(id:Int): Response<CrearRecipeResponse>

    suspend fun getAllRecipesByPatient(id:Int): Response<List<CrearRecipeResponse>>

    suspend fun createRecipe(id:Int, crearRecipeRequest: CrearRecipeRequest): Response<CrearRecipeResponse>

    suspend fun updateRecipe(id: Int,crearRecipeRequest: CrearRecipeRequest): Response<CrearRecipeResponse>

    suspend fun deleteRecipeById(id: Int): Response<Void>

}