package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CrearRecipeRequest(
    @SerializedName("product")  var product: String = "",
    @SerializedName("weight")   var weight: Int = 0,
    @SerializedName("cant")     var cant: Int = 0,
    @SerializedName("eachHour") var eachHour: Int = 0,
    @SerializedName("cantTomas") var cantTomas: Int = 0,
)
{
    override fun toString() = Gson().toJson(this)

}