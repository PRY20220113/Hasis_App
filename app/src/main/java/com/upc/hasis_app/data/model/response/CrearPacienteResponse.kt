package com.upc.hasis_app.data.model.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CrearPacienteResponse (
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("age") var age: String,
    @SerializedName("dni") var dni: String,
    @SerializedName("email") var email: String,
    @SerializedName("gener") var gener: String,
    @SerializedName("bloodT") var bloodT: String,
    @SerializedName("chronicD") var chronicD: String,
    @SerializedName("allergy") var allergy: String,
    @SerializedName("roles") var roles: List<String>,
)
{
    override fun toString() = Gson().toJson(this)
}