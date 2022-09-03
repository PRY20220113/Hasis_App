package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CrearPacienteRequest (
    @SerializedName("name") var name: String = "",
    @SerializedName("age") var age: String  = "",
    @SerializedName("gener") var gener: String = "",
    @SerializedName("bloodT") var bloodT: String = "",
    @SerializedName("chronicD") var chronicD: String = "",
    @SerializedName("allergy") var allergy: String = "",
)
{
    override fun toString() = Gson().toJson(this)
}