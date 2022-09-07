package com.upc.hasis_app.domain.entity

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Doctor(
    var id: Int,
    var first_name: String,
    var last_name: String,
    var email: String,
    var dni: String,
    var sfees_num: String,
    var phone: String,
    var roles: List<String>,
    var password: String,
) {
    override fun toString() = Gson().toJson(this)
}
