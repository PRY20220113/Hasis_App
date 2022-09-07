package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class EditarDoctorRequest(
    @SerializedName("first_name") var name: String = "",
    @SerializedName("last_name") var surname: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("dni") var dni: String = "",
    @SerializedName("sfeesNum") var sfeesNum: String = "",
    @SerializedName("phone") var phone: String = "",
) {
    override fun toString() = Gson().toJson(this)
}

