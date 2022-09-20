package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CreateRecipeRequest(
    @SerializedName("patientId")  var patientId: Int = 0,
    @SerializedName("doctorId")   var doctorId: Int = 0,
    @SerializedName("medicines")  var medicines: MutableList<CreateMedicineRequest> = mutableListOf(),
    @SerializedName("description")   var description: String = "",
)
{
    override fun toString(): String = Gson().toJson(this)

}