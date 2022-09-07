package com.upc.hasis_app.data.model.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LoginDoctorResponse(
    @SerializedName("id") var id: String,
    @SerializedName("first_name") var name: String,
    @SerializedName("last_name") var surname: String,
    @SerializedName("email") var email: String,
    @SerializedName("dni") var dni: String,
    @SerializedName("sfeesNum") var sfeesNum: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("roles") var roles: List<String>,
    @SerializedName("token") var token: String
) {
    override fun toString() = Gson().toJson(this)
}