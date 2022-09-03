package com.upc.hasis_app.data.model.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CrearRecipeResponse (
    @SerializedName("id")       var id: Long,
    @SerializedName("product")  var product: String,
    @SerializedName("weight")   var weight: Int,
    @SerializedName("cant")     var cant: Int,
    @SerializedName("eachHour") var eachHour: Int,
    @SerializedName("cantTomas") var cantTomas: Int
)
{
    override fun toString() = Gson().toJson(this)

}