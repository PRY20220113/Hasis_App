package com.upc.hasis_app.domain.repository

import com.upc.hasis_app.data.model.request.ObtenerFactosRequest
import com.upc.hasis_app.data.model.response.ObtenerFactosResponse

interface ObtenerFactosRepository {

    suspend fun obtenerFactos(): ObtenerFactosResponse

}
