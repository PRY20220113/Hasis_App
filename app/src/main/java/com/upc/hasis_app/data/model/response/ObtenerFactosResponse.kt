package com.upc.hasis_app.data.model.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ObtenerFactosResponse(
    @SerializedName("fact") var fact: String,
    @SerializedName("length") var length: Int
) {
    override fun toString() = Gson().toJson(this)
}