package com.upc.hasis_app.domain.entity

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Doctor(
    var doctorId: Int,
    var license: String,
    var speciality: Speciality,
    var user: User,
) {
    override fun toString(): String = Gson().toJson(this)
}
