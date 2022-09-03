package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class CrearDoctorRequest(
    @SerializedName("name") var name: String = "",
    @SerializedName("surname") var surname: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("dni") var dni: String = "",
    @SerializedName("sfeesNum") var sfeesNum: String = "",
    @SerializedName("phone") var phone: String = "",
    @SerializedName("password") var password: String = ""
) {
    override fun toString() = Gson().toJson(this)
}

