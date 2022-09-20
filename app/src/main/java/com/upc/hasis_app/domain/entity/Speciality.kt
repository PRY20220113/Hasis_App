package com.upc.hasis_app.domain.entity

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Speciality(
    var specialityId: Int,
    var name: String,
    var description: String,
) {
    override fun toString(): String = Gson().toJson(this)
}
