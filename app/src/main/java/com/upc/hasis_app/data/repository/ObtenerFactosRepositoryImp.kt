package com.upc.hasis_app.data.repository

import com.upc.hasis_app.data.api.ApiRest
import com.upc.hasis_app.data.model.response.ObtenerFactosResponse
import com.upc.hasis_app.domain.repository.ObtenerFactosRepository

 class ObtenerFactosRepositoryImp : ObtenerFactosRepository{

     val apiRest: ApiRest;

     constructor( apiRest: ApiRest){
         this.apiRest = apiRest
     }

    override fun obtenerFactos(): ObtenerFactosResponse {
        TODO("Not yet implemented")
    }

}