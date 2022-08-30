package com.upc.hasis_app.domain.entity

import com.google.gson.Gson

data class Doctor(
    var id: Int,
    var dni: String,
    var email: String,
    var name: String,
    var password: String,
    var phone: String,
    var sfees_num: String,
    var surname: String
) {
    override fun toString() = Gson().toJson(this)
}
