package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.response.ObtenerFactosResponse
import com.upc.hasis_app.data.repository.ObtenerFactosRepositoryImp
import com.upc.hasis_app.domain.repository.ObtenerFactosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObtenerFactosUseCase @Inject constructor(
    private var obtenerFactosRepositoryImp: ObtenerFactosRepositoryImp
) {

    suspend fun obtenerFactosUseCase(): ObtenerFactosResponse{
        return obtenerFactosRepositoryImp.obtenerFactos();
    }
}