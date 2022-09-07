package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("dni") var dni: String = "",
    @SerializedName("password") var password: String = "",
) {
    override fun toString() = Gson().toJson(this)

}
