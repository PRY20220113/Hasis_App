package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CrearPacienteRequest (
    @SerializedName("name") var name: String = "",
    @SerializedName("age") var age: String  = "",
    @SerializedName("dni") var dni: String  = "",
    @SerializedName("email") var email: String  = "",
    @SerializedName("gener") var gener: String = "",
    @SerializedName("bloodT") var bloodT: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("chronicD") var chronicD: MutableList<String> = mutableListOf() ,
    @SerializedName("allergy") var allergy: MutableList<String> = mutableListOf() ,
    @SerializedName("roles") var roles: MutableList<String> = mutableListOf(),
)
{
    override fun toString() = Gson().toJson(this)
}