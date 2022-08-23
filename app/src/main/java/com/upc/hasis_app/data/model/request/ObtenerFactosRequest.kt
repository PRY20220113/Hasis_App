package com.upc.hasis_app.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ObtenerFactosRequest(
    @SerializedName("fact") var fact: String,
    @SerializedName("length") var length: Int
) {
    override fun toString() = Gson().toJson(this)
}