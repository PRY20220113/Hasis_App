package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CreateRecipeRequest(
    @SerializedName("patientId")  var product: Int = 0,
    @SerializedName("doctorId")   var weight: Int = 0,
    @SerializedName("medicines")  var medicines: MutableList<CreateMedicineRequest> = mutableListOf(),
)
{
    override fun toString(): String = Gson().toJson(this)

}