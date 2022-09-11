package com.upc.hasis_app.domain.repository

import com.upc.hasis_app.data.model.request.CreateRecipeRequest
import com.upc.hasis_app.data.model.request.UpdateMedicineRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.domain.entity.Doctor
import com.upc.hasis_app.domain.entity.Medicine
import retrofit2.Response

interface MedicineRepository {

    suspend fun getMedicineById(medicineId: Int): Response<ResponseDTO<Medicine>>


    suspend fun updateMedicine(medicineId: Int, updateMedicineRequest: UpdateMedicineRequest): Response<ResponseDTO<Medicine>>

}