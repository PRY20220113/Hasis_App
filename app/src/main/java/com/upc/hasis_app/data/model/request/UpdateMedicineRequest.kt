package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class UpdateMedicineRequest(
    @SerializedName("weight")           var weight: Int = 0,
    @SerializedName("quantity")         var quantity: Int = 0,
    @SerializedName("eachHour")         var eachHour: Int = 0,
)
{
    override fun toString(): String = Gson().toJson(this)

}