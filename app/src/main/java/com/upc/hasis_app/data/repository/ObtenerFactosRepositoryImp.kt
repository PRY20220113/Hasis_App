package com.upc.hasis_app.data.repository

import android.app.Application
import android.util.Log
import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.response.ObtenerFactosResponse
import com.upc.hasis_app.domain.repository.ObtenerFactosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObtenerFactosRepositoryImp @Inject constructor (
     private val api: ApiRest,
 ) : ObtenerFactosRepository{

    override suspend fun obtenerFactos(): ObtenerFactosResponse {
        return withContext(Dispatchers.IO) {
           val response =  api.getFactos();
            response.body()!!;
        }
    }

}