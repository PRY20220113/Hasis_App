package com.upc.hasis_app.domain.usecase

import com.upc.hasis_app.data.model.request.CreateRecipeRequest
import com.upc.hasis_app.data.model.request.UpdateMedicineRequest
import com.upc.hasis_app.data.model.response.ResponseDTO
import com.upc.hasis_app.data.repository.MedicineRepositoryImp
import com.upc.hasis_app.data.repository.RecipeRepositoryImp
import com.upc.hasis_app.domain.entity.Medicine
import com.upc.hasis_app.domain.entity.Patient
import com.upc.hasis_app.domain.entity.Recipe
import retrofit2.Response
import javax.inject.Inject

class MedicineUseCase @Inject constructor(
    private val medicineRepositoryImp: MedicineRepositoryImp
)
{

    suspend fun getMedicineById(medicineId: Int) : Response<ResponseDTO<Medicine>> {
        return medicineRepositoryImp.getMedicineById(medicineId)
    }

    suspend fun updateMedicine(medicineId: Int, updateMedicineRequest: UpdateMedicineRequest) : Response<ResponseDTO<Medicine>> {
        return medicineRepositoryImp.updateMedicine(medicineId, updateMedicineRequest)
    }

}