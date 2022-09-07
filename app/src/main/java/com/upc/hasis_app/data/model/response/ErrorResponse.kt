package com.upc.hasis_app.data.model.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code") var code: Int,
    @SerializedName("message") var message: String,
)
{
    override fun toString() = Gson().toJson(this)
}
