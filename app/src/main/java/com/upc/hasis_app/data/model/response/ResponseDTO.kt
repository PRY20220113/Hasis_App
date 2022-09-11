package com.upc.hasis_app.data.model.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ResponseDTO<T>(
    @SerializedName("httpCode")     var httpCode: Int = 0,
    @SerializedName("errorCode")    var errorCode: Int = 0,
    @SerializedName("errorMessage") var errorMessage: String = "",
    @SerializedName("data")         var data: T? = null,
) {
    override fun toString(): String = Gson().toJson(this)

}
