package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.upc.hasis_app.util.Constantes

data class CrearDoctorRequest(
    @SerializedName("first_name") var first_name: String = "",
    @SerializedName("last_name") var last_name: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("dni") var dni: String = "",
    @SerializedName("sfeesNum") var sfeesNum: String = "",
    @SerializedName("phone") var phone: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("roles") var roles: MutableList<String> = mutableListOf(),
) {
    override fun toString() = Gson().toJson(this)
}

